/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.duomai.location.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Provides I/O operations
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class IoUtils {
    protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    /** {@value} */
    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000; // milliseconds
    protected static final int MAX_REDIRECT_COUNT = 5;
    /** {@value} */
    public static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000; // milliseconds
    protected static final int connectTimeout = DEFAULT_HTTP_CONNECT_TIMEOUT;
    protected static final int readTimeout = DEFAULT_HTTP_READ_TIMEOUT;

	/** {@value} */
	public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 KB
	/** {@value} */
	public static final int DEFAULT_IMAGE_TOTAL_SIZE = 500 * 1024; // 500 Kb
	/** {@value} */
	public static final int CONTINUE_LOADING_PERCENTAGE = 75;

	private IoUtils() {
	}

	/**
	 * Copies stream, fires progress events by listener, can be interrupted by listener. Uses buffer size =
	 * {@value #DEFAULT_BUFFER_SIZE} bytes.
	 *
	 * @param is       Input stream
	 * @param os       Output stream
	 * @param listener null-ok; Listener of copying progress and controller of copying interrupting
	 * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
	 * @throws IOException
	 */
	public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener) throws IOException {
		return copyStream(is, os, listener, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Copies stream, fires progress events by listener, can be interrupted by listener.
	 *
	 * @param is         Input stream
	 * @param os         Output stream
	 * @param listener   null-ok; Listener of copying progress and controller of copying interrupting
	 * @param bufferSize Buffer size for copying, also represents a step for firing progress listener callback, i.e.
	 *                   progress event will be fired after every copied <b>bufferSize</b> bytes
	 * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
	 * @throws IOException
	 */
	public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener, int bufferSize)
			throws IOException {
		int current = 0;
		int total = is.available();
		if (total <= 0) {
			total = DEFAULT_IMAGE_TOTAL_SIZE;
		}

		final byte[] bytes = new byte[bufferSize];
		int count;
		if (shouldStopLoading(listener, current, total)) return false;
		while ((count = is.read(bytes, 0, bufferSize)) != -1) {
			os.write(bytes, 0, count);
			current += count;
			if (shouldStopLoading(listener, current, total)) return false;
		}
		os.flush();
		return true;
	}

	private static boolean shouldStopLoading(CopyListener listener, int current, int total) {
		if (listener != null) {
			boolean shouldContinue = listener.onBytesCopied(current, total);
			if (!shouldContinue) {
				if (100 * current / total < CONTINUE_LOADING_PERCENTAGE) {
					return true; // if loaded more than 75% then continue loading anyway
				}
			}
		}
		return false;
	}

	/**
	 * Reads all data from stream and close it silently
	 *
	 * @param is Input stream
	 */
	public static void readAndCloseStream(InputStream is) {
		final byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
		try {
			while (is.read(bytes, 0, DEFAULT_BUFFER_SIZE) != -1);
		} catch (IOException ignored) {
		} finally {
			closeSilently(is);
		}
	}

	public static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ignored) {
			}
		}
	}

	/** Listener and controller for copy process */
	public static interface CopyListener {
		/**
		 * @param current Loaded bytes
		 * @param total   Total bytes for loading
		 * @return <b>true</b> - if copying should be continued; <b>false</b> - if copying should be interrupted
		 */
		boolean onBytesCopied(int current, int total);
	}


    public static String getStringFromUrl(String url) throws IOException{
        Log.i("tonghu", "IoUtils, getStringFromUrl(L148): " + Thread.currentThread().getName());
        HttpURLConnection conn = null;
        conn = createConnection(url, null);
        int redirectCount = 0;
        while (conn.getResponseCode() / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
            conn = createConnection(conn.getHeaderField("Location"), null);
            redirectCount++;
        }
        InputStream imageStream;

        try {
            imageStream = conn.getInputStream();
            if (!shouldBeProcessed(conn)) {
                IoUtils.closeSilently(imageStream);
                throw new IOException("Image request failed with response code " + conn.getResponseCode());
            }
            return slurp(imageStream);
        } catch (IOException e) {
            // Read all data to allow reuse connection (http://bit.ly/1ad35PY)
            IoUtils.readAndCloseStream(conn.getErrorStream());
            throw e;
        }

    }

    public static String slurp(final InputStream is) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    /**
     * @param conn Opened request connection (response code is available)
     * @return <b>true</b> - if data from connection is correct and should be read and processed;
     *         <b>false</b> - if response contains irrelevant data and shouldn't be processed
     * @throws IOException
     */
    protected static boolean shouldBeProcessed(HttpURLConnection conn) throws IOException {
        return conn.getResponseCode() == 200;
    }

    protected static HttpURLConnection createConnection(String url, Object extra) throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        return conn;
    }

}
