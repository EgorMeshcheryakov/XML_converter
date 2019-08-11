package com.company;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.logging.Logger;

enum Type {ACCOUNT, FIO, ADDRESS, COUNTER, SUM, EMPTY }

public class Field {
    private static Logger log = Logger.getLogger(Field.class.getName());
    private final String name;
    private final Type type;
    private final Object value;
    private boolean required;
    private boolean digitOnly;
    private boolean readOnly;

    public Field(String name, Type type, String value) throws MyException {
        this.name = name;
        this.type = type;

        switch (type) {
            case ACCOUNT:
            case COUNTER:
                if(value.matches("[-+]?\\d+") ) {
                    this.value = Integer.parseInt(value);
                }
                else {
                    throw new MyException("В теге " + type + " должно быть целочисленное значение value");
                }
                break;
            case SUM:
                if(value.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                    this.value = Double.parseDouble(value);
                }
                else {
                    throw new MyException("В теге " + type + " должно быть вещественное значение value");
                }
                break;
            default:
                this.value = value;
        }
    }

    public void setRequired(boolean required) { this.required = required; }

    public void setDigitOnly(boolean digitOnly) { this.digitOnly = digitOnly; }

    public void setReadOnly(boolean readOnly) { this.readOnly = readOnly; }

    public String getName() { return this.name; }

    public Type getType() { return this.type; }

    public Object getValue() { return this.value; }

    public boolean isRequired() { return this.required; }

    public boolean isDigitOnly() { return this.digitOnly; }

    public boolean isReadOnly() { return this.readOnly; }

    public static Field toField(Node tmpField) throws MyException {
        NodeList fieldChildNodes = tmpField.getChildNodes();

        String tmpName = "";
        String tmpValue = "";
        Type tmpType = Type.EMPTY;
        boolean tmpRequired = false;
        boolean tmpDigitOnly = false;
        boolean tmpReadOnly = false;

        for (int j = 0; j < fieldChildNodes.getLength(); j++) {
            Node childField = fieldChildNodes.item(j);

            //Фильтрация от текстовых вставок, переносов строки и пробелов
            if (childField.getNodeType() == Node.TEXT_NODE) {
                String textInformation = childField.getNodeValue().replace("\n", "").trim();

                if(!textInformation.isEmpty()) {
                    log.info("Внутри элемента найден текст: " + childField.getNodeValue());
                }
                continue;
            }

            switch(childField.getNodeName()) {
                case "name":
                    tmpName = childField.getTextContent();
                    break;
                case "type":
                    tmpType = Type.valueOf(childField.getTextContent().toUpperCase());
                    break;
                case "value":
                    tmpValue = childField.getTextContent();
                    break;
                case "required":
                    tmpRequired = true;
                    break;
                case "digitOnly":
                    tmpDigitOnly = true;
                    break;
                case "readOnly":
                    tmpReadOnly = true;
                    break;
                default:
                    throw new MyException("Ошибка синтаксиса xml файла", childField);
            }
        }

        if(tmpName.isEmpty() || tmpType == null || tmpValue.isEmpty()) {
            throw new MyException("Нет одного из обязательных тегов (name, type, value)");
        }

        Field field = new Field(tmpName,tmpType,tmpValue);
        field.setDigitOnly(tmpDigitOnly);
        field.setReadOnly(tmpReadOnly);
        field.setRequired(tmpRequired);

        return field;
    }
}
