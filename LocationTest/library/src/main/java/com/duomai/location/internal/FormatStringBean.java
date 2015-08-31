package com.duomai.location.internal;

import java.lang.reflect.Field;

public class FormatStringBean {
	@Override
	public String toString() {
		 StringBuffer sb = new StringBuffer();
	       try {
	           Class<?> t = this.getClass();
	           Field[] fields = t.getDeclaredFields();
	           for (int i = 0; i < fields.length; i++) {
	              Field field = fields[i];
	              field.setAccessible(true);
	              sb.append("{");
	              sb.append(field.getName());
	              sb.append(":");
	              if (field.getType() == Integer.class) {
	                  sb.append(field.getInt(this));
	              } else if (field.getType() == Long.class) {
	                  sb.append(field.getLong(this));
	              } else if (field.getType() == Boolean.class) {
	                  sb.append(field.getBoolean(this));
	              } else if (field.getType() == char.class) {
	                  sb.append(field.getChar(this));
	              } else if (field.getType() == Double.class) {
	                  sb.append(field.getDouble(this));
	              } else if (field.getType() == Float.class) {
	                  sb.append(field.getFloat(this));
	              } else if (field.getType() != this.getClass()) {
	                  sb.append(field.get(this));
	              }
	              sb.append("}");
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return sb.toString();
	}
}
