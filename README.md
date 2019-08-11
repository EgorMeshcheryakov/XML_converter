# XML_converter
Программа преобразует найденные в заданном каталоге xml файлы в формате src.xml в формат dst.xml. Результат сохраняет в отдельном каталоге и отправляет по HTTP методом POST.

# Src.xml:
<code>
<?xml version="1.0" encoding="utf-8"?>
<Form>
    <Groups>
        <Group>
            <name>G_1</name>
            <Fields>
                <Field>
                    <name>MY_ACC</name>
                    <type>Account</type>
                    <value>12345678</value>
                    <required>1</required>
                    <digitOnly>1</digitOnly>
                </Field>
                <Field>
                    <name>MY_FIO</name>
                    <type>Fio</type>
                    <value>Иванов Иван Иванович</value>
                    <readOnly>1</readOnly>
                </Field>
                <Field>
                    <name>MY_ADDRESS</name>
                    <type>Address</type>
                    <value>Нарымская,1,2</value>
                    <readOnly>1</readOnly>
                </Field>
            </Fields>
        </Group>
        <Group>
            <name>G_2</name>
            <Fields>
                <Field>
                    <name>COUNT1</name>
                    <type>Counter</type>
                    <value>104</value>
                    <required>1</required>
                </Field>
                <Field>
                    <name>COUNT2</name>
                    <type>Counter</type>
                    <value>764</value>
                    <required>1</required>
                </Field>
                <Field>
                    <name>COUNT3</name>
                    <type>Counter</type>
                    <value>987</value>
                    <required>1</required>
                </Field>
            </Fields>
            <Groups>
                <Group>
                    <name>G_2_1</name>
                    <Fields>
                        <Field>
                            <name>PAY_SUM</name>
                            <type>Sum</type>
                            <value>100</value>
                            <required>1</required>
                        </Field>
                        <Field>
                            <name>COMM_SUM</name>
                            <type>Sum</type>
                            <value>2.5</value>
                            <readOnly>1</readOnly>
                        </Field>
                        <Field>
                            <name>TOTAL_SUM</name>
                            <type>Sum</type>
                            <value>102.5</value>
                            <required>1</required>
                        </Field>
                    </Fields>
                </Group>
            </Groups>
        </Group>
    </Groups>
</Form>
</code>
	
# Dst.xml:
<?xml version="1.0" encoding="utf-8"?>
<Data>
	<Account name="MY_ACC" required="true" digitOnly="true" value="12345678"/>
	<Fio name="MY_FIO" readOnly="true" value="Иванов Иван Иванович"/>
	<Address name="MY_ADDRESS" readOnly="true" street="Нарымская" house="1" flat="2"/>
	<Counter name="COUNT1" required="true" value="104"/>
	<Counter name="COUNT2" required="true" value="764"/>
	<Counter name="COUNT3" required="true" value="987"/>
	<Sum name="PAY_SUM" required="true" value="100.00"/>
	<Sum name="COMM_SUM" readOnly="true" value="2.50"/>
	<Sum name="TOTAL_SUM" required="true" value="102.50"/>
</Data>
