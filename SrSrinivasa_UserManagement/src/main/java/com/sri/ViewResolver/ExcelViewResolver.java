package com.sri.ViewResolver;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.sri.Entity.Employee;
import com.sri.Service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("Excel_Download")
public class ExcelViewResolver extends AbstractXlsView {

	@Autowired
	EmployeeService empSer;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		        
		        // Retrieve EmployeesList from the model
		        Page<Employee> page = (Page<Employee>) model.get("EmployeesList");

		        // Retrieve selected filters from the model
		        String selectedDepartment = (String) model.get("selectedDepartment");
		        String selectedRole = (String) model.get("selectedRole");
		        String selectedEmail = (String) model.get("selectedEmail");
		        String selectedPhone = (String) model.get("selectedPhone");

		        // Create a new Sheet
		        Sheet sheet = workbook.createSheet("Employee List");

		        // Create a Header Row
		        Row headerRow = sheet.createRow(0);
		        String[] headers = {"Email", "First Name", "Last Name", "Roles", "Father", "Department", "Phone", "Gender", "Date of Joining"};
		        for (int col = 0; col < headers.length; col++) {
		            Cell cell = headerRow.createCell(col);
		            cell.setCellValue(headers[col]);

		            // Optional: Apply some styling to the header row
		            CellStyle headerStyle = workbook.createCellStyle();
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerStyle.setFont(headerFont);
		            cell.setCellStyle(headerStyle);
		        }

		        // Iterate over pages and add employee data
		        int rowIdx = 1; // Start after the header row
		        for (int i = 0; i < page.getTotalPages(); i++) {
		            Pageable pageable = PageRequest.of(i, page.getSize());
		            page = empSer.getAllEmployees(selectedDepartment, selectedRole, selectedEmail, selectedPhone, pageable);

		            for (Employee emp : page) {
		                Row row = sheet.createRow(rowIdx++);

		                row.createCell(0).setCellValue(emp.getEmail());
		                row.createCell(1).setCellValue(emp.getFirstName());
		                row.createCell(2).setCellValue(emp.getLastName());
		                row.createCell(3).setCellValue(emp.getRoles().toString()); // Convert roles to string
		                row.createCell(4).setCellValue(emp.getFather());
		                row.createCell(5).setCellValue(emp.getDepartment());
		                row.createCell(6).setCellValue(emp.getPhoneNumber());
		                row.createCell(7).setCellValue(emp.getGender());
		                row.createCell(8).setCellValue(emp.getDateOfJoining().toString());
		            }
		        }

		        // Resize all columns to fit the content
		        for (int col = 0; col < headers.length; col++) {
		            sheet.autoSizeColumn(col);
		        }


		        // Close the workbook to free resources
		        workbook.close();
		    
		

	}

}
