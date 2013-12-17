<%@page import="java.net.InetAddress"%>
<%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="java.io.*" %>
<%@page import="java.util.Hashtable" import="javax.naming.Context"
    import="javax.naming.NamingException"
    import="javax.naming.directory.DirContext"
    import="javax.naming.directory.InitialDirContext"
    import="javax.naming.ldap.LdapContext" import="swp.*"
    import="java.sql.Connection" import="java.sql.DriverManager"
    import="java.sql.ResultSet" import="java.sql.SQLException"
    import="java.sql.Statement" import="java.util.Enumeration"
    import="HauptProgramm.*"
    import="aufgaben_db.Global"
    import="aufgaben_db.Aufgaben_DB"
    %>
    <%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="org.apache.commons.fileupload.portlet.*"%>
<%@page import="org.apache.commons.fileupload.util.*"%>
<%@page import="java.util.*,java.util.*,java.io.File,java.lang.Exception" %>







<!-- ======= SETTINGS ================================================================ -->



<!-- DEFAULTS -->
<%
//include file="defaults.jsp"//<!-- defines default Global.anzeige  (= pageToLoad), etc.  -->
//Default Login Seite/Anzeige
session.setAttribute("defaultAnzeige", "start");







//<!-- GLOBALS
//GLOBALS - DECLARE -->

//GLOBALS - INIT
//find the real path of the WebContent and save as new root of our apple tree
Global.root = 
pageContext.getServletContext().getRealPath("."/*"WebContent"*/) + "/";
Global.session = session;
Global.response = response;

//Zur Ermittlung, was anzuzeigen ist.
if (Global.anzeige  == null) {
    Global.anzeige  = "";
}
if (Global.id  == null) {
    Global.id  = "";
}

//initialize MySQL connection
//Global.msqh = new MysqlHelper();        /* DataBaseConnection */
Global.conn = Global.msqh.establishConnection(response);
//Global.sqlm = new SQL_Methods();
Global.sqlm.setConn(Global.conn);

//get calendar of now -> date/time
Global.now = GregorianCalendar.getInstance();


//LANGUAGE
String language = request.getParameter("lang");
if (language == null) {
    language = request.getParameter("language");
}
if (language != null && !language.toLowerCase().equals(Global.LANGUAGE.name().toLowerCase()) ) {
    Global.LANGUAGE = aufgaben_db.Language.getByName(language);
}
//USER ACTION - before ID page to show to allow for correction of the target site by actions!
if (request.getParameter("q") != null) {
%>
  <jsp:include page="action.inc.jsp" />
<%
}



//<!-- DECIDE: Global.anzeige  -->
// Ermitteln, was anzuzeigen ist
//ID - SEITE | PAGE
if (request.getParameter("id") != null
        /*&& !ServletFileUpload.isMultipartContent(request)*/) {
    
    Global.anzeige = request.getParameter("id");
    /*remap*/
    //expand some ids - 
    if (request.getParameter("id").equals("search")) {
        Global.anzeige = "search_result_lucene";
    }
    
}





//SAVE TO SESSION - FOR LOADING THE LAST ACTIVE PAGE
if (Global.anzeige != null && Global.anzeige != "") {
  if (!Global.anzeige.equals(session.getAttribute("anzeige"))) {
      System.out.println("Anzeige-Variable in der Session wurde aktualisiert.");
      session.setAttribute("previousAnzeige", Global.anzeige);
  }
}
//DETERMINE NEW PAGE TO LOAD /ANZEIGE
if (request.getParameter("id") != null
        && !request.getParameter("id").equals("")) {
    //load the last active page as actual page to load /anzeige
    Global.anzeige = request.getParameter("id").toString();
}
//if available
else if (session.getAttribute("anzeige") != null) {
    //load the last active page as actual page to load /anzeige
    Global.anzeige = session.getAttribute("anzeige").toString();
}
else {
    //load default
    Global.anzeige = session.getAttribute("defaultAnzeige").toString();
}
//save for next time
session.setAttribute("anzeige", Global.anzeige);

        
        
        




//AFTER THESE CHANGES LOAD ALL USER DATA
if (Global.isLoggedIn(session)) {
    
    Aufgaben_DB.loadAllSheetdrafts();

}
%>





<!-- ======= FUNCTIONS =============================================================== -->
<%@ include file="functions.inc.jsp" %>








<!-- ======= MANIPULATION END ======================================================== -->
<!-- ======= READ GLOBALS ONLY BELOW THIS LINE ======================================= -->







<!-- ======= THE PAGE - HTML DOCUMENT ================================================ -->
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<!-- COMMON SCRIPTS -->
<!-- Bootstrap -->
<script src="bootstrap/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />

<!-- JQuery plugin -->
<script src="jquery/jquery-latest.js"></script>
<script src="jquery.treeview/lib/jquery.cookie.js" type="text/javascript"></script>
<!-- 
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
-->
<!-- Autocomplete -->
<script type='text/javascript' src='jquery/jquery.autocomplete.js'></script>
<link rel="stylesheet" type="text/css" href="jquery/jquery.autocomplete.css" />

<!-- Furthermore -->
<script type='text/javascript' src='jquery/fairytale.js'></script>

<!-- Validate selections -->
<script type="text/javascript" src="js/validateSelectionDependingOnButton.js"></script>

<!-- ToolTip Preview -->
<script src="jquery/toolTipPreview.js" type="text/javascript"></script>

<!-- Main Css -->
<link rel="stylesheet" href="css/style.css" />



<title>DMUW - Aufgaben DB</title>
</head>
<body id="body" onload="document.getElementsByTagName('input')[0].focus();">
       <!-- WRAPPER -BEGIN -->
    <div id="wrapper">
    
    <a href="index.jsp?id=start">
    <span id="kopf" class="gestaltung" style="display:block;">
        <span style="/*font-variant:small-caps;*/
        display:block;font-size:2enpx;min-height:100px; width:400px; color:white; margin-left: 200px; margin-top: 20px;">
        <%=Global.display("Exercise Database") %></span>
        <%
        if (Global.isLoggedIn(session)) {
        //    out.println("LOGGED IN");
        %>
            <!--Platzhalter fuer autologout  -->
            <span id="max" style="display:block; position:absolute; top:25px;right:100px; width:200px; height:25px;color: white; font-weight: bold;">
            </span>
        <%
        }
        %>
            
    </span>
    </a>
    <span id="languageForm" method="get" action=""
            style="display:block; position:absolute;top:20px;right:40px; font-size:10pt !important; width:80px; line-height:15px !important;overflow-x:hidden; font-weight:normal;">
        <!-- Language controls  -->
        <!-- 
        <select style="width:100px" name="lang" size="<%=aufgaben_db.Language.values().length %>" onselect="this.form.submit();">
         -->
        <%
        for (aufgaben_db.Language l : aufgaben_db.Language.values()) {
            //out.print("<option value='" + l.name() + "'>" + l.toString() + "</option>");
            out.print("<a style='color:white;' href='?lang=" + l.name().toLowerCase() + "'>[" + l.toString() + "]</a>");
        }
        
        %>
        <!-- 
        </select>
         -->
   </span>


    <div id="navi" class="gestaltung">
        <%
        if (!Global.isLoggedIn(session)) { %>
            <form name = 'login_kasten' method='post' action='index.jsp' autocomplete='off'>
                <input name='Benutzer' type='text' class='login' placeholder='Benutzer'/>
                <input style='margin-left:5px;' name='passwort' type='password' class='login' placeholder='Passwort'/>
                <button name='login' class='btn btn-success' value='Login' style='margin-left:5px;'>Login</button>
                <input type='hidden' name ='q' value= 'login' />
            </form>
        <%
        } else {

            out.println(Global.display("hello") + " " + session.getAttribute("user") + "!");
            %>
            <%@include file="menue.tpl.jsp" %>
            <%
        }
        %>




        
    </div>

    <!-- INHALT -->
    <div id="inhalt" class="gestaltung">
        
        
                <%
                //preparation
                String fileToInclude = "";//start.jsp ist der Default-wert
                String loggedInAddition = "";
                String loggedInPath = "";
                //
                if (Global.isLoggedIn(session)) {
                    //nach dem login inkludiere start.in.jsp anstatt start.jsp;
                    //ID-Attribut wird im login.jsp gesetzt(Session)
                    loggedInAddition = ".in";
                    loggedInPath = "";//"in/";
                }
                
                //nach dem login inkludiere start.in.jsp anstatt start.jsp;
                //ID-Attribut wird im login.jsp gesetzt(Session)
                if (!Global.anzeige.equals("")) {
                    fileToInclude = loggedInPath + Global.anzeige + loggedInAddition + ".jsp";
                    File f = new File(pageContext.getServletContext().getRealPath(fileToInclude));
                    if (!f.exists()) {
                        //Global.message += 
                        %>
                        <div>
                            <h1>Fehler 404 | Error 404</h1><p>&nbsp;</p><p>&nbsp;</p>
                            <p>Die angeforderte Seite wurde nicht gefunden!</p>
                            <p><b>Eingeloggt?</b><br /><p>Die Seite <%=Global.anzeige %> konnte nicht aufgerufen werden.<br />
                            melden Sie sich bitte (erneut) beim System an.</p>
                        
                            <img src="data:image/jpeg;base64,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" title="" />
                        </div>
                        <%
                                ;
                    }
                    else {
                        //out.println("including file = " + fileToInclude);
                        %>
                        <jsp:include page='<%= fileToInclude %>' />
                        <%
                    }
                }
                
                //PURPOSE? WHY NOT GIVE ext_search per ID? TODO
                //Why not show something special as overlay?
                if (request.getParameter("show") != null){//for debugging/determining purpose!
                    out.println("FYI, show variable is set! show = " + request.getParameter("show"));
                }
                //PURPOSE?
                //view something special as overlay?
                if (request.getParameter("view") != null){//for determining the purpose!
                    out.println("FYI, view variable is set! view = " + request.getParameter("view"));
                    //fileToInclude = loggedInPath + request.getParameter("view") + loggedInAddition + ".jsp";
                    %>
                    <!-- rename aendern -> edit -->
                    <!--jsp:include page='<%= fileToInclude %>' /-->
                    <%
                }
                

        
        %>
                
    </div>
    <!-- GLOBAL MESSAGE -->
    <div class="messageWrapper <%= Global.messageClass %>">
        <%= Global.getMessage() %>
    </div>
    <!-- INHALT -E -->



<!-- Autologout  -->    
<%
/* AUTOLOGOUT IF NOT ALREADY AUTO-LOGGED-OUT AND LOGGED IN: */
//if (!request.getAttribute("q").equals("logout")) {
if (Global.isLoggedIn(session)) {

    int seconds = session.getMaxInactiveInterval();
    String hostname = request.getLocalName();
    
    %>
    <script type="text/javascript">
    var host = window.location.hostname;
    var port = window.location.port;
    var path = window.location.pathname;
    var initi = window.setInterval ('downcount()', 1000);
    var text = 'Autologout ';
    var zahl = <%=seconds%>;
    
    
    var dokument="http://" + host + ":" + port  + "" + path + "?q=logout";
    function downcount()
    {
      zeige = text + 'in ' + zahl + ' <%=Global.display("seconds")%> <i class="icon-bell icon-white"></i>';
      window.status = zeige ;
    
      document.getElementById('max').innerHTML = zeige ;
      zahl --;
      if (zahl < 0 ) {
        location.href = dokument;
      }
    }
    
    
    /*Use these two functions to add an onclick event for preventing the timeout from
    expiring and logging out in the middle of a setup what is very undesirable.*/
    addEvent(document, 'onclick', (function() {  zahl = <%=seconds%>;  }));
    
    
    
    </script>
        <!--<div id = "platzhalter"></div> -->
<%
} /* AUTOLOGOUT IF NOT ALREADY AUTO-LOGGED-OUT AND LOGGED IN. -E */
%>





    <div id="fuss" class="gestaltung">

        <%
        
        //experiment
        /* String realPath = 
        pageContext.getServletContext().getRealPath("WebContent/" + Global.anzeige);
        File f = new File(realPath);
        if (f.exists()) {
            for (File file : f.listFiles()) {
                  out.println(file.toString());
            }
        }
        else {
            System.out.println("No file found.");
        } */
        
        //out.println(anzeige);
         out.println(Global.version + "\t|\t" + Global.anzeige
                 + "\t|\t<span class=\"lastModified\">"+ Global.display("last modified") +": "
        + Global.filemtime(session, loggedInPath
                + Global.anzeige + loggedInAddition + ".jsp", pageContext) + "</span>");
        %>

    </div>
    </div>
</body>
</html>