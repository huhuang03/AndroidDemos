package com.example.tonghu.apidemo.app;

import android.app.ListActivity;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.tonghu.apidemo.R;

public class QuickContact extends ListActivity{
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            Contacts._ID, // 0
            Contacts.DISPLAY_NAME, // 1
            Contacts.STARRED, // 2
            Contacts.TIMES_CONTACTED, // 3
            Contacts.CONTACT_PRESENCE, // 4
            Contacts.PHOTO_ID, // 5
            Contacts.LOOKUP_KEY, // 6
            Contacts.HAS_PHONE_NUMBER, // 7
    };
	
    static final int SUMMARY_ID_COLUMN_INDEX = 0;
    static final int SUMMARY_NAME_COLUMN_INDEX = 1;
    static final int SUMMARY_STARRED_COLUMN_INDEX = 2;
    static final int SUMMARY_TIMES_CONTACTED_COLUMN_INDEX = 3;
    static final int SUMMARY_PRESENCE_STATUS_COLUMN_INDEX = 4;
    static final int SUMMARY_PHOTO_ID_COLUMN_INDEX = 5;
    static final int SUMMARY_LOOKUP_KEY = 6;
    static final int SUMMARY_HAS_PHONE_COLUMN_INDEX = 7;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
            + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
            + Contacts.DISPLAY_NAME + " != '' ))";
 
		Cursor c = getContentResolver().query(Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, select, null, 
				Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
		startManagingCursor(c);
		setListAdapter(new ContactListAdapter(this, R.layout.quick_contacts, c));
	}
	
	private static class ContactListAdapter extends ResourceCursorAdapter {

		public ContactListAdapter(Context context, int layout, Cursor c) {
			super(context, layout, c);
		}

		@Override
		public void bindView(View view, Context arg1, Cursor cursor) {
            final ContactListItemCache cache = (ContactListItemCache) view.getTag();
		    cursor.copyStringToBuffer(SUMMARY_NAME_COLUMN_INDEX, cache.nameBuffer);
            int size = cache.nameBuffer.sizeCopied;
            cache.nameView.setText(cache.nameBuffer.data, 0, size);
            final long contactId = cursor.getLong(SUMMARY_ID_COLUMN_INDEX);
            final String lookupKey = cursor.getString(SUMMARY_LOOKUP_KEY);
            cache.photoView.assignContactUri(Contacts.getLookupUri(contactId, lookupKey));
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = super.newView(context, cursor, parent);
			ContactListItemCache cache = new ContactListItemCache();
			cache.nameView = (TextView) view.findViewById(R.id.name);
			cache.photoView = (QuickContactBadge) view.findViewById(R.id.badge);
			view.setTag(cache);
			return view;
	}
		
	}
	
	final static class ContactListItemCache {
        public TextView nameView;
        public QuickContactBadge photoView;
        public CharArrayBuffer nameBuffer = new CharArrayBuffer(128);
    }
}
