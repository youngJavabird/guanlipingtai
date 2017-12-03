package light.mvc.utils;

import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.List;  
   








import light.mvc.pageModel.wnfcard.Cardd;

import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.springframework.web.multipart.MultipartFile;


   
/**  
 *  读取03版xls内的数据   临时使用
 * @author 韩逸
 *  
 *      
 *  
 */ 
public class FromExlsUtil {  
   
    public static void main(String[] args) throws IOException {  
    	
    	String file="D:/55555555.xls";
        List<Cardd> list = FromExlsUtil.readXls(file);  
        System.out.println(list);
    }  
   
    /**  
     * 读取xls文件内容  
     *  
     * @return List<XlsDto>对象  
     * @throws IOException  
     *             输入/输出(i/o)异常  
     */ 
    public static List<Cardd> readXls(String file) throws IOException {  
        InputStream is = new FileInputStream(file);  
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);  
        Cardd xlsDto = null;  
        List<Cardd> list = new ArrayList<Cardd>();  
        // 循环工作表Sheet  
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {  
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);  
            if (hssfSheet == null) {  
                continue;  
            }  
            // 循环行Row  
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {  
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);  
                if (hssfRow == null) {  
                    continue;  
                }  
                xlsDto = new Cardd();   
                HSSFCell xh = hssfRow.getCell(0);  
                if (xh == null) {  
                    continue;  
                }  
                xlsDto.setCard_code(getValue(xh)); 
                HSSFCell xm = hssfRow.getCell(1);  
                if (xm == null) {  
                    continue;  
                }  
                xlsDto.setCard_seqno(getValue(xm)); 
                HSSFCell yxsmc = hssfRow.getCell(2);  
                if (yxsmc == null) {  
                    continue;  
                }  
                xlsDto.setCard_password(getValue(yxsmc));
                HSSFCell kcm = hssfRow.getCell(3);  
                if (kcm == null) {  
                    continue;  
                }  
                xlsDto.setCard_type(getValue(kcm));
                HSSFCell cj = hssfRow.getCell(4);  
                if (cj == null) {  
                    continue;  
                }  
                xlsDto.setCard_five(getValue(cj));
                HSSFCell cp = hssfRow.getCell(5);  
                if (cp == null) {  
                    continue;  
                }  
                xlsDto.setCard_price(getValue(cp));
                HSSFCell ced = hssfRow.getCell(6);  
                if (ced == null) {  
                    continue;  
                }  
                xlsDto.setCard_endtime(getValue(ced));
                HSSFCell ce = hssfRow.getCell(7);  
                if (ce == null) {  
                    continue;  
                } 
//                xlsDto.setCard_explain(getValue(ced));
               
                list.add(xlsDto);  
            }  
        }  
        return list;  
    }  
  
    /**  
     * 得到Excel表中的值  
     *  
     * @param hssfCell  
     *            Excel中的每一个格子  
     * @return Excel中每一个格子中的值  
     */ 
    @SuppressWarnings("static-access")  
    private static String getValue(HSSFCell hssfCell) {  
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {  
            // 返回布尔类型的值  
            return String.valueOf(hssfCell.getBooleanCellValue());  
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {  
            // 返回数值类型的值  
            return String.valueOf(hssfCell.getNumericCellValue());  
        } else {  
            // 返回字符串类型的值  
            return String.valueOf(hssfCell.getStringCellValue());  
        }  
    }  
   
} 