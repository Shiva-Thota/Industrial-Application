package com.sri.ViewResolver;

import java.awt.Color;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sri.Entity.Employee;
import com.sri.Service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("Pdf_Download")
public class PdfViewResolver extends AbstractPdfView{

	@Autowired
	EmployeeService empSer;
	
	
	
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Page<Employee> page=(Page<Employee>) model.get("EmployeesList");

		// Retrieve selected filters from the model
		String selectedDepartment = (String) model.get("selectedDepartment");
		String selectedRole = (String) model.get("selectedRole");
		String selectedEmail = (String) model.get("selectedEmail");
		String selectedPhone = (String) model.get("selectedPhone");

		// Create a new PdfPTable with 8 columns
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100); // Make the table full-width
		table.setSpacingBefore(10f); // Add some space before the table

		// Add company logo
		try {
		    Image companyLogo = Image.getInstance("src\\main\\resources\\static\\images\\main header.png"); // Provide the path to your image
		    companyLogo.scaleToFit(PageSize.A4.getWidth(), 150); // Adjust height to fit the page width
		    companyLogo.setAlignment(Element.ALIGN_CENTER);
		    document.add(companyLogo);
		} catch (Exception e) {
		    e.printStackTrace(); // Handle image loading errors
		}


		// Add heading for the employee list
		String heading = "Employees List\n\n";
		Paragraph para = new Paragraph(heading, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE));
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);

		// Add selected filters (department, role, email, phone) in order
		if (!selectedDepartment.isBlank())
		    document.add(new Paragraph("Department: " + selectedDepartment, FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK)));
		if (!selectedRole.isBlank())
		    document.add(new Paragraph("Role: " + selectedRole, FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK)));
		if (!selectedEmail.isBlank())
		    document.add(new Paragraph("Email: " + selectedEmail, FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK)));
		if (!selectedPhone.isBlank())
		    document.add(new Paragraph("Phone: " + selectedPhone + "\n\n", FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK)));

		// Add table headers with styles
		PdfPCell header;
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
		Color headerBackgroundColor = new Color(52, 73, 94); // Dark gray color for headers

		String[] headers = {"Email", "Name", "Roles", "Father", "Department", "Phone", "Gender", "Date of Joining"};
		for (String colHeader : headers) {
		    header = new PdfPCell(new Phrase(colHeader, headerFont));
		    header.setBackgroundColor(headerBackgroundColor); // Set header background color
		    header.setHorizontalAlignment(Element.ALIGN_CENTER);
		    header.setPadding(10);
		    table.addCell(header);
		}

		// Retrieve and add employee data
		for (int i = 0; i < page.getTotalPages(); i++) {
		    Pageable pageable = PageRequest.of(i, page.getSize());
		    page = empSer.getAllEmployees(selectedDepartment, selectedRole, selectedEmail, selectedPhone, pageable);
		    page.forEach(emp -> {
		        table.addCell(emp.getEmail());
		        table.addCell(emp.getFirstName() + " " + emp.getLastName());
		        table.addCell(emp.getRoles().toString());
		        table.addCell(emp.getFather());
		        table.addCell(emp.getDepartment());
		        table.addCell(emp.getPhoneNumber());
		        table.addCell(emp.getGender());
		        table.addCell(emp.getDateOfJoining().toString());
		    });
		}

		// Add table to the document and close it
		document.add(table);
		document.close();

		
		
	}

}
