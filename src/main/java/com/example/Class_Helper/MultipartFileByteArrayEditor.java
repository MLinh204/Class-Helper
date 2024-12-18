package com.example.Class_Helper;

import org.springframework.beans.propertyeditors.ByteArrayPropertyEditor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileByteArrayEditor extends ByteArrayPropertyEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        // Ignore the text value, we'll use the MultipartFile instead
    }
    @Override
    public void setValue(Object value) {
        if (value instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) value;
            try {
                super.setValue(file.getBytes());
            } catch (IOException e) {
                throw new IllegalArgumentException("Error converting MultipartFile to byte[]", e);
            }
        } else {
            super.setValue(value);
        }
    }
}
