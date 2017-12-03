package ytk.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机数、随即字符串工具
 * User: leizhimin
 * Date: 2008-11-19 9:43:09
 */
public class RandomUtils {
	 public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    public static final String numberChar = "0123456789";

	    /**
	     * 返回一个定长的随机字符串(只包含大小写字母、数字)
	     *
	     * @param length 随机字符串长度
	     * @return 随机字符串
	     */
	    public static String generateString(int length) {
	        StringBuffer sb = new StringBuffer();
	        Random random = new Random();
	        for (int i = 0; i < length; i++) {
	            sb.append(allChar.charAt(random.nextInt(allChar.length())));
	        }
	        return sb.toString();
	    }

	    /**
	     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	     *
	     * @param length 随机字符串长度
	     * @return 随机字符串
	     */
	    public static String generateMixString(int length) {
	        StringBuffer sb = new StringBuffer();
	        Random random = new Random();
	        for (int i = 0; i < length; i++) {
	            sb.append(allChar.charAt(random.nextInt(letterChar.length())));
	        }
	        return sb.toString();
	    }

	    /**
	     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	     *
	     * @param length 随机字符串长度
	     * @return 随机字符串
	     */
	    public static String generateLowerString(int length) {
	        return generateMixString(length).toLowerCase();
	    }

	    /**
	     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	     *
	     * @param length 随机字符串长度
	     * @return 随机字符串
	     */
	    public static String generateUpperString(int length) {
	        return generateMixString(length).toUpperCase();
	    }

	    /**
	     * 生成一个定长的纯0字符串
	     *
	     * @param length 字符串长度
	     * @return 纯0字符串
	     */
	    public static String generateZeroString(int length) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < length; i++) {
	            sb.append('0');
	        }
	        return sb.toString();
	    }

	    /**
	     * 根据数字生成一个定长的字符串，长度不够前面补0
	     *
	     * @param num       数字
	     * @param fixdlenth 字符串长度
	     * @return 定长的字符串
	     */
	    public static String toFixdLengthString(long num, int fixdlenth) {
	        StringBuffer sb = new StringBuffer();
	        String strNum = String.valueOf(num);
	        if (fixdlenth - strNum.length() >= 0) {
	            sb.append(generateZeroString(fixdlenth - strNum.length()));
	        } else {
	            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
	        }
	        sb.append(strNum);
	        return sb.toString();
	    }

	    /**
	     * 根据数字生成一个定长的字符串，长度不够前面补0
	     *
	     * @param num       数字
	     * @param fixdlenth 字符串长度
	     * @return 定长的字符串
	     */
	    public static String toFixdLengthString(int num, int fixdlenth) {
	        StringBuffer sb = new StringBuffer();
	        String strNum = String.valueOf(num);
	        if (fixdlenth - strNum.length() >= 0) {
	            sb.append(generateZeroString(fixdlenth - strNum.length()));
	        } else {
	            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
	        }
	        sb.append(strNum);
	        return sb.toString();
	    }

	    public static void main(String[] args) {
	        //System.out.println(generateLowerString(5));
	        //System.out.println(toFixdLengthString(123, 5));
	       //List<Integer> list=getDiffNO(10);  
/*	        for (Integer i : list) {  
	            System.out.println(i);  
	        }*/
	        //System.out.println(JsonUtils.objectToJson(list));
	    	System.out.println(FourPwd());
	    }
	    
	    /**
	     * 生成n个不同的随机数，且随机数区间为[1,n]
	     * @param n
	     * @return
	     * */
	    public static List<Integer> getDiffNO(int n){
	        // 生成 [0-n) 个不重复的随机数
	        // list 用来保存这些随机数
	        List<Integer> list = new ArrayList<Integer>();
	        Random rand = new Random();
	        boolean[] bool = new boolean[n];
	        int num = 0;
	        for (int i = 0; i < n; i++) {
	            do {
	                // 如果产生的数相同继续循环
	                num = rand.nextInt(n);
	            } while (bool[num]);
	            bool[num] = true;
	            list.add(num+1);
	        }
	        return list;
	    }
	    public static String FourPwd(){
	    	Random rdm = new Random();
	    	String hash1 = Integer.toHexString(rdm.nextInt());
	    	String pwd = hash1.substring(0, 4);
	    	return pwd;
	    }
}
