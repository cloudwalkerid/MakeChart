/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makechart.Process;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import makechart.Entity.TableResult;
import makechart.Entity.TableResultHasil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ASUS
 */
public class MakeExcell implements MakeBase {

    private static final String FolderCsv = System.getenv("APPDATA") + "\\MakeChart\\MakeTableCsv";
    private static final String FolderExcell = System.getenv("APPDATA") + "\\MakeChart\\MakeExcell";
    private static final String FolderPicture = System.getenv("APPDATA") + "\\MakeChart\\MakePicture";
    private static final String Folderhtml = System.getenv("APPDATA") + "\\MakeChart\\MakeHtml";

    @Override
    public void doProcess(Object object) {
        if (object instanceof TableResultHasil) {
            TableResultHasil tableResultHasil = (TableResultHasil) object;
            if ((new File(FolderExcell + "\\" + tableResultHasil.getObjectTable().getUid() + ".xlsx")).exists()) {
                (new File(FolderExcell + "\\" + tableResultHasil.getObjectTable().getUid() + ".xlsx")).delete();
            }
            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

            /* CreationHelper helps us create instances for various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Sheet
            Sheet sheet = workbook.createSheet("hasil");

            int panjangColumn = 0;
            int panjangRow = 0;
            for (TableResult itemResult : tableResultHasil.getTableResults()) {
                if (itemResult.getRowCategory().size() > panjangRow) {
                    panjangRow = itemResult.getRowCategory().size();
                }
                if (itemResult.getColumnCategory().size() > panjangColumn) {
                    panjangColumn = itemResult.getColumnCategory().size();
                }
            }
            int maxColumn = panjangColumn;
            int maxRow = panjangRow;
            panjangColumn = panjangColumn * 2;
            panjangRow = panjangRow * 2;

            if (tableResultHasil.getObjectTable().getValues().size() > 1) {
                panjangColumn = panjangColumn + 1;
            }
            if (panjangColumn != 0 && panjangRow != 0) {
                sheet.addMergedRegion(new CellRangeAddress(5, 5 + panjangColumn - 1, 0, panjangRow - 1));
            }

            Row titleRow = sheet.createRow(0);
            Cell oneTitleCell = titleRow.createCell(0);
            oneTitleCell.setCellValue(tableResultHasil.getObjectTable().getName());
            Row descRow = sheet.createRow(1);
            Cell oneDescCell = descRow.createCell(0);
            oneDescCell.setCellValue(tableResultHasil.getObjectTable().getDesc());

            int startRow = 5 + panjangColumn;
            int startColumn = panjangRow;
            System.out.println(startRow + "|" + startColumn);
            ArrayList<MakeTableHelper> alreadyHaveColumnHeader = new ArrayList<>();
            ArrayList<MakeTableHelper> alreadyHaveRowHeader = new ArrayList<>();
            for (TableResult item : tableResultHasil.getTableResults()) {
                boolean makeColumnHeader = true;
                boolean makeRowHeader = true;
                int rowIndex = 0;
                int columnIndex = 0;
                for (MakeTableHelper itemColumn : alreadyHaveColumnHeader) {
                    if (itemColumn.getLastIDColumn().equals(item.getColumnCategory()
                            .get(item.getColumnCategory().size() - 1).getNameColumnHasil())) {
                        makeColumnHeader = false;
                        columnIndex = itemColumn.getColumnStart();
                    }
                }
                for (MakeTableHelper itemRow : alreadyHaveRowHeader) {
                    if (itemRow.getLastIDRow().equals(item.getRowCategory()
                            .get(item.getRowCategory().size() - 1).getNameColumnHasil())) {
                        makeRowHeader = false;
                        rowIndex = itemRow.getRowStart();
                        break;
                    }
                }
                //System.out.println("212.."+makeRowHeader+"|"+makeColumnHeader);
                if (makeColumnHeader) {
                    if (alreadyHaveColumnHeader.size() == 0) {
                        MakeTableHelper a = item.makeColumnHeader(5, startColumn,
                                maxColumn, sheet);
                        alreadyHaveColumnHeader.add(a);
                        columnIndex = startColumn;
                    } else {
                        MakeTableHelper a = item.makeColumnHeader(5, alreadyHaveColumnHeader.get(alreadyHaveColumnHeader.size() - 1).getColumnLast() + 1,
                                maxColumn, sheet);
                        alreadyHaveColumnHeader.add(a);
                        columnIndex = alreadyHaveColumnHeader.get(alreadyHaveColumnHeader.size() - 1).getColumnStart();
                    }
                }
                if (makeRowHeader) {
                    if (alreadyHaveRowHeader.size() == 0) {
                        MakeTableHelper b = item.makeRowHeader(startRow, 0,
                                maxRow, sheet);
                        alreadyHaveRowHeader.add(b);
                        rowIndex = startRow;
                    } else {
                        MakeTableHelper b = item.makeRowHeader(alreadyHaveRowHeader.get(alreadyHaveRowHeader.size() - 1).getRowLast() + 1,
                                0, maxRow, sheet);
                        alreadyHaveRowHeader.add(b);
                        rowIndex = alreadyHaveRowHeader.get(alreadyHaveRowHeader.size() - 1).getRowStart();
                    }
                }
                item.makeInside(rowIndex, columnIndex, sheet);
            }
            try {
                FileOutputStream fileOut = new FileOutputStream(FolderExcell + "\\" + tableResultHasil.getObjectTable().getUid() + ".xlsx");
                workbook.write(fileOut);
                fileOut.close();
//                System.out.println("\""+FolderExcell.replaceAll("\\\\", "/")+"/"+ tableResultHasil.getObjectTable().getUid() + ".xlsx"+"\"");
                Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler "+ FolderExcell.replaceAll("\\\\", "/") + "/" + tableResultHasil.getObjectTable().getUid() + ".xlsx");
                //Runtime.getRuntime().exec("\"" + FolderExcell.replaceAll("\\\\", "/") + "/" + tableResultHasil.getObjectTable().getUid() + ".xlsx" + "\"");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String doOneArray(TableResultHasil tableResultHasil, int row, int column) {
        return "";
    }

    @Override
    public void cancel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
