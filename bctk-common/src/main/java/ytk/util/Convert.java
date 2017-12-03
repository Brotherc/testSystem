package ytk.util;

import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.*;
import net.sourceforge.pinyin4j.format.exception.*;
 
public class Convert {
   
 /*
  * 获取汉字全拼
  */
  public static String getAllLeter(String args) {
	String conver="";
	System.out.println(args);
    char [] t1 =args.toCharArray(); 
     String[] t2 = new String[10];
    //宣告輸出的格式
    net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat t3 = new
        HanyuPinyinOutputFormat();
    t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    t3.setVCharType(HanyuPinyinVCharType.WITH_V);
    //取回陣列循環的數量
    int t0=t1.length;
    try {
      for (int i=0;i<t0;i++)
      {
        t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
//        System.out.println(t2);
        if(t1[i]=='荥'){
        	t2[0]="xing";
        }
        if(t2==null){
        	conver+=t1[i];
        }else{
        conver+=t2[0];
        }
      }
    }
    catch (BadHanyuPinyinOutputFormatCombination e1) {
      e1.printStackTrace();
    }
    return conver;
  }
  /*
   * 获取汉字首字母
   */
  public static String  getFirstLeter(String args) {
		String conver="";
		
	    char [] t1 =args.toCharArray(); 
	     String[] t2 = new String[10];
	    //宣告輸出的格式
	    net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat t3 = new
	        HanyuPinyinOutputFormat();
	    t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	    t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	    t3.setVCharType(HanyuPinyinVCharType.WITH_V);
	    //取回陣列循環的數量
	    int t0=t1.length;
	    try {
	      for (int i=0;i<t0;i++)
	      {
	        t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
	        if(t1[i]=='荥'){
	        	t2[0]="xing";
	        }
//	        System.out.println(t1[i]);
	        if(t2==null){
	        
	        	conver+=t1[i];
	        }else{
	        	 conver+=t2[0].substring(0,1);
	        }
	       
	      }
	    }
	    catch (BadHanyuPinyinOutputFormatCombination e1) {
	      e1.printStackTrace();
	    }
	    return conver;
	  }
//  public static void main(String args[]){
//	  System.out.println("spell==========="+Convert.getAllLeter("荥阳市信号量123"));
//	  System.out.println("spell==========="+Convert.getFirstLeter("荥阳市信号量111"));
//  }
}

