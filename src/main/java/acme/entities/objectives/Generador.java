
package acme.entities.objectives;

public class Generador {

	public static void main(final String[] args) {
		String[] cambio = ("key,instantiationMoment,title,description,priority,isCritical,initialExecutionPeriod,endingExecutionPeriod,link\r\n"
			+ "objective-01,2000/01/01 00:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-02,2000/01/01 00:01,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-03,2010/01/01 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-04,2022/07/29 23:58,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-05,2022/07/29 23:59,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-06,2019/06/08 13:00,T,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-07,2019/06/08 13:00,TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-08,2019/06/08 13:00,TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-09,2019/06/08 13:00,TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-10,2019/06/08 13:00,الباب 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-11,2019/06/08 13:00,标题 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-12,2019/06/08 13:00,Τίτλος 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-13,2019/06/08 13:00,Название 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-14,2019/06/08 13:00,제목 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n" + "objective-15,2019/06/08 13:00,Título 1,D,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-16,2019/06/08 13:00,Título 1,DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-17,2019/06/08 13:00,Título 1,DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-18,2019/06/08 13:00,Título 1,DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-19,2019/06/08 13:00,Título 1,الوصف 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n" + "objective-20,2019/06/08 13:00,Título 1,说明 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-21,2019/06/08 13:00,Título 1,Περιγραφή 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-22,2019/06/08 13:00,Título 1,Описание 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-23,2019/06/08 13:00,Título 1,설명 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-24,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-25,2019/06/08 13:00,Título 1,Descripción 1,MEDIUM,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-27,2019/06/08 13:00,Título 1,Descripción 1,HIGH,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-28,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-29,2019/06/08 13:00,Título 1,Descripción 1,LOW,true,2022/07/30 14:00,2022/08/05 15:00,https://www.samsung.com\r\n"
			+ "objective-30,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2019/06/08 13:00,2100/01/01 00:00,https://www.samsung.com\r\n"
			+ "objective-31,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2019/06/08 13:01,2100/01/01 00:00,https://www.samsung.com\r\n"
			+ "objective-32,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2050/06/08 13:00,2100/01/01 00:00,https://www.samsung.com\r\n"
			+ "objective-33,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2099/12/31 23:58,2100/01/01 00:00,https://www.samsung.com\r\n"
			+ "objective-34,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2099/12/31 23:59,2100/01/01 00:00,https://www.samsung.com\r\n"
			+ "objective-35,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/07/30 15:00,https://www.samsung.com\r\n"
			+ "objective-36,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/07/30 15:01,https://www.samsung.com\r\n"
			+ "objective-37,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2060/07/30 15:00,https://www.samsung.com\r\n"
			+ "objective-38,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2099/12/31 23:59,https://www.samsung.com\r\n"
			+ "objective-39,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2100/01/01 00:00,https://www.samsung.com\r\n" + "objective-40,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,null\r\n"
			+ "objective-41,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://w.es\r\n"
			+ "objective-42,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.la-prueba-de-las-pruebas.com\r\n"
			+ "objective-43,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.pruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebaprueba-prueba-prueba-prueba-prueba.com\r\n"
			+ "objective-44,2019/06/08 13:00,Título 1,Descripción 1,LOW,false,2022/07/30 14:00,2022/08/05 15:00,https://www.pruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebapruebaprueba-prueba-prueba-prueba-prueba-prueba.com")
				.split("\r\n");
		for (String c : cambio) {
			String[] cc = c.split(",");
			System.out.println(cc[1] + "," + cc[6] + "," + cc[7]);
		}
	}

}
