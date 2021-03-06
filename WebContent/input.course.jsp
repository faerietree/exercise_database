<%@page import="java.util.*,
		core.Global,
		java.sql.ResultSet;"
%>
<%
String id = "course";
if (request.getParameter("node_id") != null) {
	id = request.getParameter("node_id");
}

if (request.getParameter("default") != null) {
	%>
		<input type="hidden" name="course_old" class="required noSpecialChars"
				id="<%= id %>" value="<% out.print(request.getParameter("default")); %>"
				onkeyup="deactivateIfFilledOrActivateIfEmpty(this, '<%= id %>_select');"
		/>
		<!--
		The input is hidden as at first teachers/lecturers should be motivated to use existing courses.
		May be shown via JavaScript if already existing courses are not enough.
		-->
		<input type="text" name="course" class="required noSpecialChars" style="display:none;"
				id="<%= id %>" value="<% out.print(request.getParameter("default")); %>"
				onkeyup="deactivateIfFilledOrActivateIfEmpty(this, '<%= id %>_select');"
		/>
	<%
}
else {
	%>
		<input type="text" name="course" class="required noSpecialChars"
				id="<%= id %>" value=""
				onkeyup="deactivateIfFilledOrActivateIfEmpty(this, 'course_select');"
		/>
	<%
}
%>
	<select name="course_select" class="" id="<%= id %>_select"
			onchange="document.getElementById('course').value=this.value;<%= request.getParameter("onchange") %>;">
<%
// Fetch all distinct courses from database
ResultSet res = Global.query("SELECT DISTINCT `course` FROM `sheetdraft`");
// Get length
int resL = 0;
if (res != null) {
	// Generate the option fields
	String output = "";
	boolean isOptionSelected = false;
	while (res.next()) {
		// Add option
		output += "<option ";
		output += " title='" + Global.decodeUmlauts(res.getString("course")) + "'";
		if (res.getString("course").equals(request.getParameter("default"))) {
			output += " selected='selected'";
			isOptionSelected = true;
		}
		output += " value='"+ res.getString("course") +"' ";
		output += ">" + Global.decodeUmlauts(res.getString("course")) + "</option>";
		++resL;
	}
	// Tackle memory leaks by closing result set and its statement properly:
	Global.queryTidyUp(res);
	// Now that we now the count of the objects: prepend the default:
	output = "<option " + (isOptionSelected ? "" : " selected='selected' ")
			+ " disabled='disabled'>---" + resL + " "
			+ Global.display("entries found") + ":----</option>" + output;
	out.println(output);

}
else {
	out.print("<option selected='selected' disabled='disabled'>-------</option>");
}
%>
	</select>

