<link href="${staticPath}/css/userguide/webUserGuide.css" rel="stylesheet" type="text/css" />

<div id="userGuideModal" class="wrapper">
  
  <div class="navWrapper">
    <img class="logoimg" width="252" height="104" src="${staticPath}/images/mainmenu/logo.fw.png"></img>

    <div class="vertical">
      <ul>
         <!-- <li>
          <label for="NYEHMS">NYEHMS</label>
          <input type="radio" name="verticalMenu" id="NYEHMS" />
        </li> -->
        <li>
          <label for="Home">Dashboard Graphs</label>
          <input type="radio" name="verticalMenu" id="Home" />
        </li>
        <li id="">
          <label for="personSearch">Person Search</label>
          <input type="radio" name="verticalMenu" id="personSearch" />
        </li>
        <li>
          <label for="">Assignment</label>
          <input type="radio" name="verticalMenu" id="assignment" />
          <div>
            <ul>
              <li><a href="#">User / Provider Association</a></li>
              <li><a href="#">View / Search</a></li>
              <li><a href="#">Reports</a></li>
            </ul>
          </div>
        </li>
        <li>
          <label for="CaseDataManagement">Case Data Management</label>
          <input type="radio" name="verticalMenu" id="CaseDataManagement" />
          <div>
            <ul>
              <li><a id="Merge">Merge</a></li>
              <li><a id="UnMerge">Unmerge</a></li>
            </ul>
          </div>
        </li>
        <li id="standardization_tab">
          <label for="Standardization">Standardization</label>
          <input type="radio" name="verticalMenu" id="Standardization" />
          <div>
            <ul>
              <li><a id="Provider">Provider</a>
                <ul>
                  <li id="providerUnstandardized_tab"><a id="ProviderUnstandardized">Unstandardized</a></li>
                  <li id="providerSearchManage_tab"><a id="ProviderSearchManage">Search / Manage</a></li>
                  <li id="providerManualReview_tab"><a id="ProviderManualReview">Manual Review</a></li>
                </ul>  
              </li>
              <li><a id="Facility">Facility</a>
                <ul>
                  <li><a id="FacilityUnstandardized">Unstandardized</a></li>
                  <li><a id="FacilitySearchManage">Search / Manage</a></li>
                  <li><a id="FacilityManualReview">Manual Review</a></li>
                </ul>
              </li>
              <li><a id="EHarsFacility">eHars Facility</a></li>
            </ul>
          </div>
        </li>
        <li>
          <label for="review">Review</label>
          <input type="radio" name="verticalMenu" id="review" />
          <div>
            <ul>
              <li><a href="#">Assignments</a></li>
              <li><a href="#">PRF Data Entry</a></li>
              <li><a href="#">PRF QA</a></li>
            </ul>
          </div>
        </li>
        <li>
          <label for="lab">Lab</label>
          <input type="radio" name="verticalMenu" id="lab" />
          <div>
            <ul>
              <li><a href="#">Lab Maintenance</a></li>
              <li><a href="#">LOINC / Result Maintenance</a></li>
              <li><a href="#">Errors</a></li>
              <li><a href="#">Process</a></li>
              <li><a href="#">Reports</a></li>
            </ul>
          </div>
        </li>
        <li id="administration_tab">
          <label for="Administration">Administration</label>
          <input type="radio" name="verticalMenu" id="Administration" />
          <div>
            <ul>
              <li><a id="SearchManageProfile">Search/Manage Profile</a></li>
              <li><a id="SearchProfile">Search Profile</a></li>
              <li><a id="CreateNewUser">Create New User</a></li> 
              <li><a id="SystemMessageManage">System Message Manage</a></li>
              <li><a id="SystemMessage">System Message</a></li>
            </ul>
          </div>
        </li>
        <li>
          <label for="SOP">Operating Procedures</label>
          <input type="radio" name="verticalMenu" id="SOP" />
          <div>
            <ul>
              <li><a id="ConsolidatedMergeUnmerge">Consolidated Merge Unmerge</a></li>            
              <li><a id="ClericalReviewProtocol">Clerical Review Protocol</a></li>
            </ul>
          </div>
        </li>
        <li>
          <label for="Glossary">Glossary</label>
          <input type="radio" name="verticalMenu" id="Glossary" />
          <div>
            <ul>
              <li><a id="GlossaryofTerms">Glossary of Terms</a></li>            
            </ul>
          </div>
        </li>
      </ul>
    </div>
  </div> 
  
  <div class="textLabel" style="width:50%">
    <b>User Guide</b>
     <a href="${staticPath}/docs/UserGuideNyehms_07_15_2019.pdf" class="pdfDownload" download="NYEHMS Help Document" ></a>
  </div>
  <div class="contentWrapper"> 

    <!-- <div class="logoContent hideContent"> 
      <h1>User Guide</h1>          
      <img class="logo" width="315" height="130" src="${staticPath}/images/mainmenu/logo.fw.png"></img>  
    </div> -->


    <div class="homeContent hideContent"> 
      <h1>Home</h1>          
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Home/1.jpg"></img></p>  
    </div>

    <div class="personSearchContent hideContent"> 
      <h1>Person Search</h1>  
      <p>The Person Search is accessed via the global menu bar.<img width="700" height="100" src="${staticPath}/images/userGuide/PersonSearch/1.jpg"></img></p> 

      <p>Click on <img width="100" height="40" src="${staticPath}/images/userGuide/PersonSearch/2.jpg"></img> button that will take you to "Search Criteria". </p>

      <p><img width="700" height="250" src="${staticPath}/images/userGuide/PersonSearch/3.jpg"></img></p>

      <p>Here conduct the search from above screen and that will open "Search results" in other tab</p>
      <p><img width="700" height="350" src="${staticPath}/images/userGuide/PersonSearch/4.jpg"></img></p>
      <p>Click on any STATENO from the above table, which will open "Person View" Tab along with "Document Timeline" Tab.</p>

      <h3>Person View</h3>
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/PersonSearch/5.jpg"></img></p>

      <p><img width="200" height="40" src="${staticPath}/images/userGuide/PersonSearch/6.jpg"></img>We can mark this STATENO "Bad Merge or not". If we mark it as Bad Merge, This will go and sit in Case Data Management Unmerge Page.</p>

      <p>Click on <img width="25" height="25" src="${staticPath}/images/userGuide/PersonSearch/7.jpg"> icon, which will open a dialog with information.

      <p><img width="600" height="250" src="${staticPath}/images/userGuide/PersonSearch/8.jpg"></img></p>
        
      <p>Click on <img width="25" height="25" src="${staticPath}/images/userGuide/PersonSearch/9.jpg"> icon, which will expand and shows the associated document available.</p>

      <p><img width="600" height="270" src="${staticPath}/images/userGuide/PersonSearch/10.jpg"> </p>
  
      <p>When we click on "HCD4", which will open a lab document view.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/PersonSearch/11.jpg"> </p>

      <h3>Document Timeline</h3>

      <p>Click on "Document Timeline" Tab,</p>
      <p><img width="700" height="150" src="${staticPath}/images/userGuide/PersonSearch/12.jpg"> </p>

      <p>When we click on "HCD4", which will open a lab document view.</p>

      <p><img width="700" height="350" src="${staticPath}/images/userGuide/PersonSearch/13.jpg"> </p>  

      <p>In "Lab Document" View, you will see both <img width="200" height="40" src="${staticPath}/images/userGuide/PersonSearch/14.jpg"> and <img width="200" height="40" src="${staticPath}/images/userGuide/PersonSearch/15.jpg"> buttons. 
      
      <p>If we click on <img width="200" height="40" src="${staticPath}/images/userGuide/PersonSearch/14.jpg">, which will open a "Document View" tab.</p>

      <p><img width="700" height="350" src="${staticPath}/images/userGuide/PersonSearch/16.jpg"> </p> 

      <p>After editing the document, click on <img width="200" height="40" src="${staticPath}/images/userGuide/PersonSearch/17.jpg">  button.
      That will prompt a dialog </p>
       <p><img width="600" height="200" src="${staticPath}/images/userGuide/PersonSearch/18.jpg"> </p> 

    </div>

    <div class="administrationContent hideContent"> 
      <h1>Administration</h1>  

      <p>The administrative module is accessed via the global menu bar. <img width="700" height="100" src="${staticPath}/images/userGuide/Administration/1.jpg"></img></p>

      <p>The sub-menu items displayed under the Administration menu is driven by the user's permissions.  A user must have permissions granted to that functionality in order to see the menu item.</p>

      <p>When a user is granted all of the permissions below, <br> <img width="200" height="150" src="${staticPath}/images/userGuide/Administration/2.jpg"></img></p>

      <p>The user will see the following menu/submenu items. <br> <img width="250" height="300" src="${staticPath}/images/userGuide/Administration/3.jpg"></img></p>
    </div>
      
    <div class="searchManageProfileContent hideContent">   
      <h2>Search/Manage Profile</h2>
      <p><img width="250" height="300" src="${staticPath}/images/userGuide/Administration/4.jpg"></p> 
      <h3>Search/Manage Profile</h3> 

      <p>The search/manage profile is used to locate and maintain a NYEHMS user profile.  Selecting 'Search/Manage Profile' opens the search screen.  A user can search on User ID, First Name, Middle Name, Last Name, and Email address, Region and / or Role.  The search is not case sensitive.  The '*' character can be used as a wild card anywhere in the search string. (i.e., entering j* in the first name field returns all users whose name begins with a 'j'; entering *son* in the last name, returns all users who have 'son' anywhere in their last name).  If 'Search' is clicked without entering any search criteria, all NYEHMS users will be returned.<img width="700" height="250" src="${staticPath}/images/userGuide/Administration/5.jpg"></p> 

      <p>Once the user clicks 'Search,' the results will be displayed on the page, showing User Id, First Name, Last Name, Start Date and End Date.  Clicking any column headers will re-sort the result grid by the selected column header.  Clicking the '+' sign in the first column, next to the User ID, displays the region/roles associated with that user.  When the user selects a row from the results, the 'Manage User' and 'Terminate User' buttons will be enabled.<img width="700" height="350" src="${staticPath}/images/userGuide/Administration/6.jpg"></p>

      <p>The user can perform an additional search by clicking on the Search bar displayed above the results grid.  This area can open and close simply by clicking on the search bar.</p>
      <p>Expanding the row of a user, displays the roles associated with that user.<img width="700" height="400" src="${staticPath}/images/userGuide/Administration/7.jpg"></p>
       
      <p>Manage User:
        <ul>
          <li>Manage User: Select the row of the user to mange<img width="600" height="100" src="${staticPath}/images/userGuide/Administration/8.jpg"></li><br>
          <li>Click Manage User button<br><img width="100" height="40" src="${staticPath}/images/userGuide/Administration/9.jpg"></li>
        </ul>  
      </p>


      <p>The user's profile will be displayed in another tab.  Here the user can edit First Name, Last Name, Email Address and Security and Confidentiality training date(C&S).  The User Id is not editable.  To update the C&S date, click on <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/10.jpg">  and select the date of the user's last C&S training from the calendar pop up.  C&S training is valid for 1 year and will be monitored by a system process and warn a user with a system message if their training date will expire in 30 days or less.  If C&S date expires, the user will not be able to access the application.<img width="700" height="350" src="${staticPath}/images/userGuide/Administration/11.jpg"></p>
               
      <p>Click 'Save' to save changes. <br><img width="100" height="40" src="${staticPath}/images/userGuide/Administration/12.jpg"></p>   

      <p>The user will be prompted with a confirmation message to save the user details.  Upon confirmation, the user will receive a success or error message.</p>

      <p>To add a new MAC Address, click on <img width="25" height="30" src="${staticPath}/images/userGuide/Administration/13.jpg">. Enter MAC address information in the new window and click 'Add.'   The new MAC Address will be visible in the MAC Address table.  A user must have at least one active MAC address to access NYEHMS.  A user cannot access the NYEHMS application from a device containing a MAC address that is not associated with the user.<img width="400" height="200" src="${staticPath}/images/userGuide/Administration/14.jpg"></p>

      <p>New Mac Address Added and confirmation message Dialog will show up<br>
      Now you can see the Added Mac Address 21-21-21-21-21-21 under "User Mac Address"<img width="600" height="150" src="${staticPath}/images/userGuide/Administration/15.jpg"></p>

      <p>To 'Retire' a user's MAC Address, select the MAC address from the User Mac Address grid and click <img width="25" height="30" src="${staticPath}/images/userGuide/Administration/16.jpg">.  A window will appear allowing the user to enter the End Date.  The user must click on (insert calendar image) and select a date from the calendar pop up.  Click 'Update' button to save changes.  Once the MAC address has been retired, the use can no longer access NYEHMS from the PC associated with that MAC address.<img width="450" height="200" src="${staticPath}/images/userGuide/Administration/17.jpg"></p>

      <p>Selecting 'UPDATE', will present a "Success (Error) Message Dialog", select 'OK' button and changes will be reflected in User Mac Address Table.<br><img width="500" height="150" src="${staticPath}/images/userGuide/Administration/18.jpg"></p>
       
      <p>Region Role Permissions -</p>

      <p>This grid displays the region and roles associated with this user.  The start date and end date are specific to each region/role.  Click '+' sign within a row to display user's permissions associated with that region/role. The user's permissions will be displayed by module with a list of current permissions for that module.  A user can only Edit permissions for Region Roles that have not be retired (end date is blank). <img width="600" height="150" src="${staticPath}/images/userGuide/Administration/19.jpg"></p>

      <p>To edit the permissions associated a Region Role, select that Region Role from the grid and click <img width="25" height="30" src="${staticPath}/images/userGuide/Administration/16.jpg"> </p>

      <p>A new window will appear with the permissions available for the Region Role selected.  From here, users can update permissions within a region role or retire a region role.<img width="600" height="350" src="${staticPath}/images/userGuide/Administration/20.jpg"></p>

      <p>Clicking the "Retire" Button  <img width="100" height="40" src="${staticPath}/images/userGuide/Administration/21.jpg">  prompts the user for the End Date.  The user must click on <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/10.jpg"> and select a date from the calendar pop up. Click on "Update"</p>

      <p>To update permissions from the Region Role Details modal window, the user can select or deselect any of the permissions listed and then click 'Update.'<br><img width="500" height="150" src="${staticPath}/images/userGuide/Administration/22.jpg"> </p>

      <p>Success/ Error Message Dialog will show up
      To add a new Region Role, click .  The user will be prompted to select the Region and roles from the dropdowns. <br><img width="500" height="150" src="${staticPath}/images/userGuide/Administration/23.jpg"> </p>

      <p>Once the region and roles have been selected, a list of permissions associated with selected region/role will appear with all permissions pre-selected.  The user has the ability to deselect any permission in the list.  Click 'Add' to save the new Region Role.</p>

      <p>Selecting a region and "Supervisor" role will allow the following permissions to be added:<img width="600" height="350" src="${staticPath}/images/userGuide/Administration/24.jpg"></p>

      <p>Selecting a region and "Co-Clerical" role will allow the following permissions to be added:<img width="600" height="400" src="${staticPath}/images/userGuide/Administration/25.jpg"></p>

      <p>Selecting a region and "CO-Surveillance Coordinator" role will allow the following permissions to be added:<img width="600" height="350" src="${staticPath}/images/userGuide/Administration/26.jpg"></p>

      <p>Selecting a region and "DBA" role will allow the following permissions to be added:<img width="600" height="350" src="${staticPath}/images/userGuide/Administration/27.jpg"> </p>

      <p>Selecting a region and "Worker" role will allow the following permissions to be added:<img width="600" height="350" src="${staticPath}/images/userGuide/Administration/28.jpg"> </p>

      <p>After selecting and deselecting permissions, click 'Add' <img width="100" height="40" src="${staticPath}/images/userGuide/Administration/29.jpg">, you'll receive a new region role added message dialog.<br><img width="600" height="150" src="${staticPath}/images/userGuide/Administration/30.jpg"> </p>

      <p>Terminate User:</p>

      <p>From the search results grid, you can terminate a user.  Select the user row <img width="600" height="100" src="${staticPath}/images/userGuide/Administration/31.jpg"> </p>
       
      <p>And then click 'Terminate User' button <img width="170" height="40" src="${staticPath}/images/userGuide/Administration/32.jpg">.  A confirmation message will appear to make sure the user really wants to terminate this user in NYEHMS. <br><img width="300" height="150" src="${staticPath}/images/userGuide/Administration/33.jpg"> </p>
         
      <p>If you select "Yes", you will receive either "Exception / Success" Dialog Message </p>
      <img width="500" height="150" src="${staticPath}/images/userGuide/Administration/34.jpg">
    </div>
    
    <div class="searchProfileContent hideContent">  
      <h2>Search Profile</h2>
      <p><img width="250" height="300" src="${staticPath}/images/userGuide/Administration/35.jpg"></p>  
      <h3>Search Profile</h3> 
      <p>Selecting 'Search Profile' opens the search screen.  A user can search on User ID, First Name, Middle Name, Last Name, and Email address, Region and / or Role.  The search is not case sensitive.  The '*' character can be used as a wild card anywhere in the search string. (i.e., entering j* in the first name field returns all users whose name begins with a 'j'; entering *son* in the last name, returns all users who have 'son' anywhere in their last name).  If 'Search' is clicked without entering any search criteria, all NYEHMS users will be returned. <img width="600" height="250" src="${staticPath}/images/userGuide/Administration/36.jpg"> </p>
       

      <p>Once the user clicks 'Search,' the results will be displayed on the page, showing User Id, First Name, Last Name, Start Date and End Date.  Clicking any column headers will re-sort the result grid by the selected column header.  Clicking the '+' sign in the first column, next to the User ID, displays the region/roles associated with that user. <img width="700" height="400" src="${staticPath}/images/userGuide/Administration/37.jpg"></p>  
       

      <p>The user can perform an additional search by clicking on the Search bar displayed above the results grid.  This area can open and close simply by clicking on the search bar. </p>

      <!-- <p>The Administrative module is used to manage users and permissions of the NYEHMS application.
      Users can be added, deactivate and maintained using this module.  The Administrative module is also used to create, activate, and modify System Messaging viewed by all active NYEHMS users.</p>        
      <p> The menu items displayed under the Administration module is driven by the user's permissions.  A user must have permissions granted to that functionality in order to see the menu item. </p>

      <h2>Search/Manage</h2>
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/1.png"></img></p>
  
      <h3>Search/Manage Profile</h3>
      <p>Selecting 'Search/Manage Profile' opens the search screen.  A user can search on User ID, First Name, Middle Name, Last Name, and Email address, Region and / or Role.  The search is not case sensitive.  The '*' character can be used as a wild card anywhere in the search string. (i.e., entering j* in the first name field returns all users whose name begins with a 'j'; entering *son* in the last name, returns all users who have 'son' anywhere in their last name).  If 'Search' is clicked without entering any search criteria, all NYEHMS users will be returned.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/2.png"></img></p>

      <p>Once the user clicks 'Search,' the results will be displayed on the page, showing User Id, First Name, Last Name, Start Date and End Date.  Clicking any column headers will re-sort the result grid by the selected column header.  Clicking the '+' sign in the first column, next to the User ID, displays the region/roles associated with that user.  When the user selects a row from the results, the 'Manage User' and 'Terminate User' buttons will be enabled.</p> 
      
       <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/3.png"></img></p>

      <p>The user can perform an additional search by clicking on the Search bar displayed above the results grid.  This area can open and close simply by clicking on the search bar.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/4.png"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/5.png"></img></p>
      
      <p>Manage User: 
        <ul> 
          <li>Manage User: Select the row of the user to mange</li>
          <li>Click Manage User button</li>
        </ul> 
      </p>
      
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/6.png"></img></p>

      <p>The user's profile will be displayed in another tab.  Here the user can edit First Name, Last Name, Email Address and Security and Confidentiality training date(C&S).  The User Id is not editable.  To update the C&S date, click on <img src="${staticPath}/images/userGuide/calendar.png"></img> and select the date of the user's last C&S training from the calendar pop up.  C&S training is valid for 1 year and will be monitored by a system process and warn a user with a system message if their training date will expire in 30 days or less.  If C&S date expires, the user will not be able to access the application.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/7.png"></img></p>
      
      <p>Click 'Save' to save changes.  The user will be prompted with a confirmation message to save the user details, after confirming a success or error message will appear. </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/8.png"></img></p>
      
      <p>To add a new MAC Address, click on <img src="${staticPath}/images/buttons/Add.png"></img>.  Enter MAC address information in the new window and click 'Add.' The new MAC Address will be visible in the MAC Address table.  A user must have at least one active MAC address to access NYEHMS.  A user cannot access the NYEHMS application from a device containing a MAC address that is not associated with the user.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/9.png"></img></p>
      
      <p>New Mac Address Added and confirmation message Dialog will show up</p>

      <p>Now you can see the Added Mac Address 21-21-21-21-21-21 under "User Mac Address"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/10.png"></img></p>
      
      <p>To 'Retire' a user's MAC Address, select the MAC address from the User Mac Address grid and click <img src="${staticPath}/images/buttons/Remove.png"></img>.  A window will appear allowing the user to enter the End Date.  The user must click on (insert calendar image) and select a date from the calendar pop up.  Click 'Update' button to save changes.  Once the MAC address has been retired, the use can no longer access NYEHMS from the PC associated with that MAC address.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/11.png"></img></p>
      
      <p>Selecting 'UPDATE', will present a "Success (Error) Message Dialog", select 'OK' button and changes will be reflected in User Mac Address Table.</p>

      <p>Region Role Permissions -</p>

      <p>This grid displays the region and roles associated with this user.  The start date and end date are specific to each region/role.  Click '+' sign within a row to display user's permissions associated with that region/role. The user's permissions will be displayed by module with a list of current permissions for that module.  A user can only Edit permissions for Region Roles that have not be retired (end date is blank).</p>

      <p>To edit the permissions associated a Region Role, select that Region Role from the grid and click <img src="${staticPath}/images/buttons/Add.png"></img></p>

      <p>Image goes here</p>

      <p>A new window will appear with the permissions available for the Region Role selected.  From here, users can update permissions within a region role or retire a region role.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/12.png"></img></p>
      
      <p>Clicking the "Retire" Button prompts the user for the End Date.  The user must click on <img src="${staticPath}/images/userGuide/calendar.png"></img> and select a date from the calendar pop up. Click on "Save"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/13.png"></img></p>
      
      <p>To update permissions from the Region Role Details modal window, the user can select or deselect any of the permissions listed and then click 'Update.'</p>
      
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/14.png"></img></p>
      
      <p>Success/ Error Message Dialog will show up</p>

      <p>To add a new Region Role, click <img src="${staticPath}/images/buttons/Add.png"></img>. The user will be prompted to select the Region and roles from the dropdowns.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/15.png"></img></p>
      
      <p>Once the region and roles have been selected, a list of permissions associated with selected region/role will appear with all permissions pre-selected.  The user has the ability to deselect any permission in the list.  Click 'Add' to save the new Region Role.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/16.png"></img></p>

      <p>Selecting a region and "Supervisor" role will allow the following permissions to be added:</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/17.png"></img></p>
      
      <p>Selecting a region and "Co-Clerical" role will allow the following permissions to be added:</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/18.png"></img></p>
      
      <p>Selecting a region and "CO-Surveillance Coordinator" role will allow the following permissions to be added:</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/19.png"></img></p>
      
      <p>Selecting a region and "DBA" role will allow the following permissions to be added:</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/20.png"></img></p>
      
      <p>Selecting a region and "Worker" role will allow the following permissions to be added:</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/21.png"></img></p>
      
      <p>After selecting and deselecting permissions, click 'Add', you'll receive a new region role added message dialog.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/22.png"></img></p>
      
      <p>Terminate User:</p>

      <p>From the search results grid, you can terminate a user.  Select the user row and then click 'Terminate User' button.  A confirmation message will appear to make sure the user really wants to terminate this user in NYEHMS.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/23.png"></img></p>
      
      <p>If you select "Yes", you will receive either "Exception / Success" Dialog Message</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SearchManageProfile/24.png"></img></p>
 -->
    </div>


    <div class="createNewUserContent hideContent"> 
      <h1>Create New User</h1>   
      <p><img width="250" height="300" src="${staticPath}/images/userGuide/Administration/38.jpg"></p> 

      <h3>Create New User</h3> 
      <p>When creating a new user profile, the user id must be validated against the Health Commerce System.  A user must have a valid HCS account before adding them to NYEHMS.<img width="600" height="200" src="${staticPath}/images/userGuide/Administration/39.jpg"> </p>

      <p>Entering the user id of an active user, will present the following message 'User is Active.  Click here to go to search and manage profile page.'<br><img width="600" height="200" src="${staticPath}/images/userGuide/Administration/40.jpg"> </p>  

      <p>Clicking the 'click here' link will bring the user to the Manage User screen.<img width="600" height="400" src="${staticPath}/images/userGuide/Administration/41.jpg">  </p>

      <p>If the user id was validated and not found to be an active user, the New User Details grid would appear on the screen.   The first and last name fields are prepopulated from their HCS account information.  The following fields are editable within the New User Details grid:<br>
        1. First Name - First Name cannot start with a space, First Name cannot contain any Special characters. Acceptable Characters a-z,A-Z,space ?<br>
        2. Middle Name - Middle Name cannot start with a space, Middle Name cannot contain any Special characters<br>
        3. Last Name - Last Name cannot start with a space, Last Name cannot contain any Special characters other than period(.), hyphen(-), apostrophe(')<br>
        4. Email Address<br>
        5. Mac address<br>
        6. Start date<br>
        7. C & S date (confidentiality and security training date)
      </p>

      <p>When adding a new user, the user must be associated with a valid region role.  This is done by selecting a menu option in the Region data field.<br> <img width="600" height="400" src="${staticPath}/images/userGuide/Administration/42.jpg"> <img width="600" height="350" src="${staticPath}/images/userGuide/Administration/43.jpg"> <img width="600" height="200" src="${staticPath}/images/userGuide/Administration/44.jpg"> </p>

      <p>Once the region and role is selected from the dropdowns, the suggested permissions will appear on the page; these can be deselected.  Once finished, click Submit. <img width="600" height="350" src="${staticPath}/images/userGuide/Administration/45.jpg"> </p>
       
      <p>After selecting the "submit" button, an "Exception/Success" Message Dialog will appear. </p>

      <!-- <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/CreateNewUser/1.png"></img></p>

      <p>When creating a new user profile, the user id must be validated against the Health Commerce System.  A user must have a valid HCS account before adding them to NYEHMS.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/CreateNewUser/2.png"></img></p>
      
      <p>Entering the user id of an active user, will present the following message 'User is Active. Click here to go to search and manage profile page.'</p>
      
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/CreateNewUser/3.png"></img></p>

      <p>Clicking the 'click here' link will bring the user to the Manage User screen.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/CreateNewUser/4.png"></img></p>   
      
      <p>If the user id was validate and not found to be an active user, the New User Details grid would appear on the screen.   The first and last name fields are prepopulated from their HCS account information.  The following fields are editable within the New User Details grid:
        <p>1. First name - First Name cannot start with a space, First Name cannot contain any Special characters. Acceptable Characters  a-z,A-Z,space ? <br>
          2. Middle name - Middle Name cannot start with a space, Middle Name cannot contain any Special characters<br>
          3. Last name - Last Name cannot start with a space, Last Name cannot contain any Special characters other than period(.), hyphen(-), apostrophe(') <br>
          4. email address <br>
          5. mac address <br>
          6. start date <br>
          7. c & S date   
      </p>    
      
      <p>Adding a New User Details with Region Role</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/CreateNewUser/5.png"></img></p> 
      
      <p>Once the region and role is selected from the dropdowns, the permissions will appear on the page.  Once finished, click Submit.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/CreateNewUser/6.png"></img></p> 
      
      <p>After selecting the "submit" button, an "Exception/Success" Message Dialog will appear.</p>
 -->
    </div>


    <div class="systemMessageManageContent hideContent"> 
      
      <h1>System Message Manage </h1>

      <p><img width="250" height="300" src="${staticPath}/images/userGuide/Administration/46.jpg"></p>

      <p>System wide messaging is managed from the 'System Message Manage' sub-menu item on the Administration menu.  Selecting this will load the System Message page.  The active tab displays the 'Active Messages' within NYEHMS.  Inactive messages (those deleted or expired) are displayed in the 'Inactive Messages' tab. <img width="600" height="300" src="${staticPath}/images/userGuide/Administration/47.jpg"></p>
 
      <p>To Add a New Message Click the <img width="120" height="40" src="${staticPath}/images/userGuide/Administration/48.jpg"> button, a new window will appear allowing the user to type in the system message and a start and end date associated with the message. </p>

      <p>Validation rules - end date cannot be before start date, start date cannot be earlier than created date. <img width="500" height="150" src="${staticPath}/images/userGuide/Administration/49.jpg"> </p>
          
      <p>To enter the start or end date, the user must click  <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/10.jpg"> and select a date from the calendar pop up. All fields are required.  Once all information has been entered, click 'Save'  <img width="100" height="40" src="${staticPath}/images/userGuide/Administration/12.jpg">  and you will see message success/exception. <img width="500" height="150" src="${staticPath}/images/userGuide/Administration/50.jpg"></p>

      <p>An existing message in the active tab can be selected and updated or deleted <img width="600" height="350" src="${staticPath}/images/userGuide/Administration/51.jpg"></p>
       
      <p>If the start data has already started, it can be deleted.
      When the user clicks 'Update' <img width="100" height="40" src="${staticPath}/images/userGuide/Administration/52.jpg">, the selected message will appear in a new window. <br> <img width="500" height="150" src="${staticPath}/images/userGuide/Administration/53.jpg"> </p>  
       

      <p>If the message has been updated, Click 'Update' <img width="100" height="40" src="${staticPath}/images/userGuide/Administration/52.jpg"> to save changes.  You will see message updated success/exception. <br><img width="500" height="150" src="${staticPath}/images/userGuide/Administration/54.jpg"></p>
       
      <p>The message history 'icon' <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/55.jpg"> will also be displayed. <img width="600" height="100" src="${staticPath}/images/userGuide/Administration/56.jpg"></p>

      <p>When you click on <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/55.jpg"> history icon, that will bring all the history related to that record. <img width="600" height="100" src="${staticPath}/images/userGuide/Administration/57.jpg"></p>     

      <p>When user click 'Delete' <img width="100" height="40" src="${staticPath}/images/userGuide/Administration/58.jpg">, the selected message will get deleted and you will see message deleted success/exception.<br> <img width="600" height="150" src="${staticPath}/images/userGuide/Administration/59.jpg"></p>

      <p>The "Inactive Message" Tab contains all "deleted/ expired messages.  Users can sort on any of the column headers just by clicking on them. <br><img width="600" height="300" src="${staticPath}/images/userGuide/Administration/60.jpg"></p>
       
      <p>Clicking <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/55.jpg">, a new window will open with the history of that message. <img width="600" height="150" src="${staticPath}/images/userGuide/Administration/61.jpg"></p>
       
      <p>This icon <img width="20" height="20" src="${staticPath}/images/userGuide/Administration/66.jpg"> indicates that message was deleted. Columns which don't have <img width="20" height="20" src="${staticPath}/images/userGuide/Administration/66.jpg"> icon that means the message is expired due to the end date and time. </p>

      <!-- <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SystemMessageManage/1.png"></img></p> 

      <p>Selecting the 'System Message Manage' menu item from the Administration menu, loads the System Message page.  The active tab displays the 'Active Messages' within NYEHMS.  Inactive messages (those deleted or expired) are displayed in the 'Inactive Messages' tab.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SystemMessageManage/2.png"></img></p> 
      
      <p>To Add a New Message Click the "New Message" Button, a new window will appear allowing the user to type in the system message and a start and end date associated with the message.  To enter the start or end date, the user must click <img src="${staticPath}/images/userGuide/calendar.png"></img> and select a date from the calendar pop up. All fields are required.  Once all information has been entered, click 'Save' and the new message will appear in the 'Active Message' tab.</p>
      
      <p>Need to correct the below statement</p>
      
      <p>Validation rules - end date cannot be before start date, start date cannot be earlier than created date, etc.</p>
      
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SystemMessageManage/3.png"></img></p> 

      <p>Selecting the "Save" Button, "Success" Message Dialog will show up.</p>
      
      <p>Once a message is selected from the Active Message grid, the Update and Delete buttons will be enabled.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SystemMessageManage/4.png"></img></p> 
      
      <p>When the user clicks 'Update', the selected message will appear in a new window.  If the message has been updated, the message history will also be displayed. Click 'Update' to save changes.</p>

      <p>Screenshot goes here</p>
      
      <p>Updating the Message results in successfully updated message dialog will show up.</p>
      
      <p>The "Inactive Message" Tab contains all "deleted/ expired" messages.  Users can sort on any of the column headers just by clicking on them.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SystemMessageManage/5.png"></img></p> 
      
      <p>Clicking <img src="${staticPath}/images/icons/history_icon.jpg"></img>, a new window will open with the history of that message.</p> 

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Administration/SystemMessageManage/6.png"></img></p> 
 -->
    </div>

    <div class="systemMessageContent hideContent"> 
      
      <h1>System Message</h1>
      <p><img width="250" height="300" src="${staticPath}/images/userGuide/Administration/62.jpg"></p>
      <p>Selecting the 'System Message' menu item from the Administration menu, loads the System Message page.  This is a read only version of the System Message Manage module. The active tab displays the 'Active Messages' within NYEHMS. <img width="600" height="350" src="${staticPath}/images/userGuide/Administration/63.jpg"></p>  
 
      <p>Inactive messages (those deleted or expired) are displayed in the 'Inactive Messages' tab. <img width="600" height="350" src="${staticPath}/images/userGuide/Administration/64.jpg"></p>

      <p>Clicking <img width="25" height="25" src="${staticPath}/images/userGuide/Administration/55.jpg">, a new window will open with the history of that message. <img width="600" height="100" src="${staticPath}/images/userGuide/Administration/65.jpg"></p>
       
      <p>This icon <img width="20" height="20" src="${staticPath}/images/userGuide/Administration/66.jpg">  represents that message was deleted. Columns which don't have <img width="20" height="20" src="${staticPath}/images/userGuide/Administration/66.jpg">  icon that means the message is expired due to the end date and time. </p>

    </div>  

    <div class="caseDataManagementContent hideContent"> 
      
      <h1>Case Data Management</h1>

      <p>The Case Data Management Module is used primarily by central office staff to maintain the data associated with individuals in the registry.  The major functionality of this module is to combine two different individuals into one person (merge), and to move incorrectly associated documents from one person to another (unmerge). </p>
      <p>The Case Data Management Module is located on the global menu bar.<br><img width="600" height="120" src="${staticPath}/images/userGuide/CaseDataManagement/1.jpg"></p>
     
    </div> 

    <div class="mergeContent hideContent"> 
      
      <h1>Merge</h1>
      <p>The Merge functionality can be accessed via the submenu of the Case Data Management Module. <br><img width="250" height="200" src="${staticPath}/images/userGuide/CaseDataManagement/2.jpg"></p>
     
      <p>The main page of the Merge Screen contains 2 boxes, 'Pair the STATENO' and 'Flagged STATENOs for review':<img width="600" height="300" src="${staticPath}/images/userGuide/CaseDataManagement/3.jpg"></p>

      <p><b>1. Pair the STATENOs</b> - Allows the user to enter a new pair of STATENOs to be flagged as the same person and supporting comments, if desired. When 'Flag as Same' is clicked, the STATENOs will be verified as valid and active STATENOs.  The application will also validate that the pair is not currently flagged and in the 'Flagged STATENOs for review' (2) table.<img width="600" height="100" src="${staticPath}/images/userGuide/CaseDataManagement/4.jpg"></p>

      <p><img width="600" height="300" src="${staticPath}/images/userGuide/CaseDataManagement/5.jpg"><br>After validation, the pair is inserted in the 'Flagged STATENOs for review' table and a new tab will open displaying the person view of the 2 STATENOs that were just entered. This tab allows the user to now merge the new pair, if desired <i>(refer to following 'Merging' section).</i> </p>

      <p><b>2. Flagged STATENOs for review </b> - Displays the list of all pairs of STATENOs that have been previously flagged as the same person.  In addition to the pairs of STATENOs, the list also contains the name of the user that entered the pair, the date that it was entered and any historical comments pertaining to the specific pair of STATENOs.  Clicking on a row loads the pair of STATENOs in a new tab displaying the person view of both STATENOs.  This tab allows the review and merge or reject the pair <i>(refer to following 'Merging' section)</i>.  <img width="600" height="200" src="${staticPath}/images/userGuide/CaseDataManagement/6.jpg"></p>

      <h3>Merging:</h3>
      <p>Merging functionality is presented in a tab titled 'Person View'.  The tab contains the pair of STATENOs in a numbers "Double panel Compare window", left and right.    Displayed is the 'flattened' version of each person, showing only 1 value for each data element, usually the best indicated version.  (Person Module Person View Documentation) <img width="700" height="350" src="${staticPath}/images/userGuide/CaseDataManagement/7.jpg"></p>

      <p>There are vertical and horizontal scroll bars that scroll both persons simultaneously. Consistent with the person view functionality, this screen allows the user to click on the '+'' sign to view all of the variations of that data element and further drill down to view the individual documents that  contain that specific data element variation. The Document Timeline displays all of the documents that can be accessed in the person view in a chronologically display.  (Person Module Document Timeline Documentation)</p>
      <p>Clicking on a '+' sign on either side will open the data elements for both STATENOs side by side for comparison. <img width="600" height="350" src="${staticPath}/images/userGuide/CaseDataManagement/8.jpg"> </p>

      <p> There are 3 buttons across the top of the merge screen:</p>

      <p>1. <img width="100" height="40" src="${staticPath}/images/userGuide/CaseDataManagement/9.jpg"> Merge - Selectin this button  will prompt for confirmation that you want to merge these STATENOs, allow a comment to be added and combine both  STATENOs into one, keeping one STATENO, retiring the other, and giving a success message.  If one or both of the STATENOs is flagged to be same as another STATENO, the system inform the user with the following dialog.<br><img width="600" height="180" src="${staticPath}/images/userGuide/CaseDataManagement/10.jpg"></p>

      <p>Click Here will present similar pairs to the user to view and potentially merge. <img width="600" height="120" src="${staticPath}/images/userGuide/CaseDataManagement/11.jpg"><br>When merging is complete, the pair is removed from the 'flagged STATENOs for review' list.</p>

      <p>2. <img width="120" height="40" src="${staticPath}/images/userGuide/CaseDataManagement/12.jpg"> Not a Match - Not a match won't match the documents into one and will remove this pair from the 'flagged STATENOs for review' list.</p>

      <p>3. <img width="100" height="40" src="${staticPath}/images/userGuide/CaseDataManagement/13.jpg"> Skip - skip won't perform any action on the STATENOs.  The opened record for person view tab will close and bring the user to flagging screen.</p>

    </div>  

    <div class="unMergeContent hideContent"> 
      
      <h1>UnMerge</h1>

       <p>The Unmerge functionality can be accessed via the submenu of the Case Data Management Module.<br><img width="250" height="200" src="${staticPath}/images/userGuide/CaseDataManagement/14.jpg"></p>
     
      <p>The main screen from the Unmerge submenu presents a table with a list of persons previously flagged as bad merges (2 or more people mangled together).    The table also lists the user that flagged this person as a bad merge and the date it was flagged.  The last column contains a history icon that will be visible if there were historical comments associated with this STATENO.  Clicking on the icon will pop up a window with all comments, user that entered the comment and date in which it was entered.  The Person Module contains the functionality allowing users to flag an individual as a bad merge and add comments. (Person Module Documentation)<img width="600" height="250" src="${staticPath}/images/userGuide/CaseDataManagement/15.jpg"></p>

      <p>Click on any row from the ’flagged STATENO for review’ table, will open “Bad Merge Review” Tab.  This tab contains a double panel, side-by-side comparison window.  “Double panel Compare window” with <br> 1. The person flagged as a bad merge in presented in the left panel using the person view tab and document timeline. (Person Module for technical guidance)<br> 2. The right side is loaded with the search page of the Person Module, giving the user the ability to search for the person they want to move documents into. <br><img width="750" height="400" src="${staticPath}/images/userGuide/CaseDataManagement/16.jpg"></p>

      <p>The general workflow is for a user to search extensively for a person to move documents into.  If they cannot find an individual, they can generate a new STATENO or person  <img width="150" height="40" src="${staticPath}/images/userGuide/CaseDataManagement/17.jpg"><br>to move the documents into, but not until they perform at least one search. The user also has the ability from this screen to unflag the individual as a bad merge. <br> <img width="200" height="40" src="${staticPath}/images/userGuide/CaseDataManagement/18.jpg"></p>

      <p>The move button in the middle of the split panel (or on individual documents in the document viewer) is disabled until a search is complete with or without results returned.</p>
      <p>A user can then navigate from the alias popup showing different variations of the data, drill to the specific documents then open the document and click the move button to move the document from the person on the left to the person on the right. </p>

      <p>A user can also utilize the document timeline: <img width="700" height="350" src="${staticPath}/images/userGuide/CaseDataManagement/19.jpg"></p>

      <p>Highlighting documents and selecting the move button with prompt for confirmation:<img width="500" height="200" src="${staticPath}/images/userGuide/CaseDataManagement/20.jpg"></p>

      <p>After we click "Yes" button from above screen, the document moved from "left window panel to right window panel", and you will receive a Message – "Moved the document to STATENO 0010000791".<img width="700" height="350" src="${staticPath}/images/userGuide/CaseDataManagement/21.jpg"></p>

      <p>After you finish moving the documents from "Left window panel to Right window panel". You can go back to "Person View" Tab and mark this as "not a bad merge", if no other documents need to be moved.<img width="700" height="350" src="${staticPath}/images/userGuide/CaseDataManagement/22.jpg"></p>

      <p>After you mark it as "No", you will receive a message "Unmarked as bad merge".<img width="600" height="150" src="${staticPath}/images/userGuide/CaseDataManagement/23.jpg"></p>

      <p>The state number "xxxxxxxxxx" removed from the "bad merge" tab "Flagged STATENOs for review" table.<img width="700" height="250" src="${staticPath}/images/userGuide/CaseDataManagement/24.jpg"></p>


    </div>

    <div class="standardizationContent hideContent"> 
      <h1>Standardization</h1> 

      <p> Standardization - contains three sub links </p>
          <p>1. Provider - Unstandardized , Search/Mange , Manual Review <br>
             2. Facility <br>
             3. eHars Facility </p>
        </ul>
      </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/1.jpg"></img></p>   

    </div>

    <div class="providerUnstandardizedContent hideContent"> 
      <h1>Standardization -> Provider -> Unstandardized </h1> 

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/2.jpg"></img></p> 

      <p>In Provider Standardization - "Search for provider Address" has ability to search with any of First Name, Last Name, etc..</p>    

      <p>Validation Rules - </p>

      <p>
          1. <b>First Name</b> - cannot start with a space, cannot contain any Special characters, Acceptable Characters a-z,A-Z,space ? <br>
          2. <b>Middle Name</b> - cannot start with a space, cannot contain any Special characters, Acceptable Characters a-z,A-Z,space ? <br>
          3. <b>Last Name</b> - cannot start with a space, cannot contain any Special characters other than period(.), hyphen(-), apostrophe('), Acceptable Characters  a-z,A-Z,.-',space ? <br>
          4. <b>Street Address</b> - Cannot contain special characters other than period(.), comma(,), hyphen(-), ampersand(&), hash(#), fwd slash (/), apostrophe('), Acceptable Characters  Space,0-9,a-z,A-Z,.,-&#/' <br>
          5. <b>City</b> - cannot start with a Space, cannot contain special characters other than dash(-), period(.), space, Acceptable Characters  a-Z,A-Z,space<br>
          6. <b>Zip Code</b> - should only contain digits, Acceptable Characters 0-9 <br>
          7. <b>Phone</b> - should only contain numbers, Acceptable Characters 0-9 <br>
      </p>

      <p>In Provider Standardization  Raw Provider Address Needing Standardization table has three functionalities.</p>

      <p>1. Refresh - will reload the raw provider address table data<br>
         2. Search - has the ability to search with "Name, Address, etc...""  and  "equal, not equal, etc.."</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/3.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/4.jpg"></img></p>

      <p>3. Flag - </p>
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/5.png"></img></p>

      <p>If we conduct a Search with Empty fields - That will bring all the Records in Search Results table.</p>

      <p>After conducting Search - 'Search for Provider Address' container is expandable/ Collapsible. The Search Results of a "Provider Address" are displayed below.</p> 

      <p>In Search Results</p> 

      <p>1. Red color Represents  - Provider Name <br>
         2. Green Color Represents - Provider Address <br>
         3. Brown Color Represents - Raw Provider Address <br> 
         4. Yellow Color Represents - Selected Record</p>

      <p>"Provider Name" - has expand/collapse "+"" icon is available. "Provider Name" will have linked to multiple "Provider Address".</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/6.png"></img></p>

      <p>New Provider & Address - is always enabled without any selection<br>
      (The "Raw Provider Address" did not find a match with any of the "Provider" in the results table. At that time we add a "New Provider & Address")</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/7.png"></img></p>

      <p>New Address - is enabled only when you select any "Provider Name" with "Provider Address" <br>
      (The "Raw Provider Address" is different from "Provider Address" in the results table at that time we add a "New Address" - We cannot edit the "Provider Name")
      </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/8.png"></img></p>

      <p>Standardize - is enabled only when you select any "Provider Name"<br>
        (The "Raw Provider Address" will have a match with the "Standardize Provider Name & Address" will link)</p>

      <p>In Provider Standardization -> select a record from "Raw Provider Address" table and hit "New Provider & Address" button from the "Search results for Provider" table. It will open "Adding New Provider and Address" Dialog.</br> (You can Add or Edit "Provider Name" and "Provider Address" and "Submit") </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/9.png"></img></p>

      <p>After Submit "Standardize Confirmation" Dialog will appear with "Add and Link" Button.</p>

      <p>Technically we are linking a newly created provider and address to "Raw Provider". When we hit "Add and Link" The New Provider and Address will add a link and standardize it to the raw provider address.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/10.png"></img></p>

      <p>From Search results when we select a "Provider" (selected record will highlight in yellow color) "New Address" Button will enabled. When we click on "New Address" that will bring a dialog "Adding Address to the Existing Provider" - Here we can only Add/Edit the "Address", we cannot Edit Provider Name.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/11.jpg"></img></p>

      <p>After Submit "Standardize Confirmation" Dialog will appear with "Add and Link" Button.</p>

      <p>Technically we are linking a "New Address" to "Raw Provider Address". When we hit "Add and Link" The Raw provider will linked to newly created provider address</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/12.jpg"></img></p>

      <p>From Search results when we select a "Provider" with "Provider Address" (selected record will highlight in yellow color) "Standardize" Button will enabled. When we click on "Standardize" that will bring a dialog "Standardize Confirmation" - Here we can Link "Raw Provider" to "Provider Address" to make it standardize.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/13.jpg"></img></p>

      <p>Suppose when the address is not associated with any provider, those address we called as "Orphan". That Orphan Address found in "Standard Addresses without Provider" Tab.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/14.jpg"></img></p>

      <p>When we right click on record that will bring up the dialog with "Raw Address Linked to Standardized Address"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/15.jpg"></img></p>

      <p>Example the One Provider Address associated with multiple phone numbers</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/16.jpg"></img></p>

      <p>For Example - In Search Results we found a record is not having any provider name (Orphan) "Selected in Yellow Color" - When we hit "standardize" Button "Standardize Confirmation" Dialog will appear with "Select Provider" Button.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/17.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/18.jpg"></img></p>

      <p>When we hit "Select Provider" Button that will take to "Standard Addresses without Provider" Tab.</p>
      <p>(Note - this will show only the address (Provider Address Needing Standardization) which we selected from the above Screen.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/19.jpg"></img></p>

      <p>Conduct search to add a provider to "orphan", selected the provider from below screen and hit "Standardize"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/20.jpg"></img></p>

      <p>Linked the "Orphan address" to the provider and raw address to the address</p>
      
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/21.jpg"></img></p>

      <p>Conduct search to add a provider to "orphan", selected the provider and hit "New Provider".
      Added New Provider and hit "Submit"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/22.jpg"></img></p>

      <p>Clicked Add and Link, Added the "Orphan address" to the provider and raw address to the address</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/23.jpg"></img></p>

      <p>"Standard Addresses without Provider" Tab contains all Orphan Address. Same Actions will perform to add a provider to Orphan Address. (From Above)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/24.jpg"></img></p>

      <p>When you right click on the "Provider Address Needing Standardization", which will bring up all the "Orphan Address" which will have a "Raw Address" in a dialog</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/25.jpg"></img></p>

    </div>

    <div class="providerSearchManageContent hideContent"> 

      <h1>Standardization -> Provider -> Search / Manage </h1>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/26.jpg"></img></p>

      <p>In Standardize, Provider Search Manage -> Search Provider contains </p>
      <p>1. Provider - allows only Provider name search <br>
         2. Provider Address - allows only Provider Address search <br>
         3. Raw Provider Address - allows both Provider name and Address search </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/27.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/28.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/29.jpg"></img></p>

      <p>Searched with Provider - "Provider" <br>
      (Note - Provider Search will bring all the records under matched provider <br>
      1. provider - When we search with "provider" - matched provider records will bring all "provider address and raw provider address under matched provider"<br>
      2. provider address  - matched provider address records will bring all "raw provider address under matched provider address along with provider"<br>
      3. raw provider address - matched raw provider address records will bring all "siblings of raw provider address", with "parent provider address" along with "super parent provider" )</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/30.jpg"></img></p>

      <p>Selected work Area Type to "Provider", It will give the check box to select at least 2 providers to Merge<br>
      (Note - Merge button will enable only when you select 2 providers to Merge.)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/31.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/32.jpg"></img></p>

      <p>When we hit "Merge" Button - it will open a dialog "which provider to keep". Select one and hit "OK" Button.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/33.jpg"></img></p>

      <p>Select one and hit "OK" Button. That will merge the selected provider into "Provider Selected". Gives confirmation "providers merged"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/34.jpg"></img></p>

      <p>Selected work Area Type to "Provider Address", It will give the check box to select a provider Address</p>

      <p>(Note - Merge button will enable only when you select 2 provider addresses to Merge.
         Un-Link button will enable only when you select 1 provider Address to Un-Link)</p>
      <p>(Note - Provider Address Search will bring all the records under matched provider Address<br>
      1. provider<br>
      2. provider address<br>
      3. raw provider address )</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/35.jpg"></img></p>

      <p>Selected the provider address to unlink - Confirmation dialog will show " unlink selected sub provider address from provider xyz"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/36.jpg"></img></p>

      <p>Selected two provider address to "merge" and selected which provider address to keep for 'xyz provider'.<br>
      It will open a dialog confirmation "merge your selected addresses into 'address selected'".</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/37.jpg"></img></p>

      <p>Selected work Area Type to "Raw Provider Address", It will give the check box  to select a "Raw Provider Address"  to Un-Link</p>
      <p>(Note -  Un-Link button will enable only when you select 1 Raw Provider Address to Un-Link)</p>
      <p>(Note - Raw Provider Address Search will bring all the records under matched Raw Provider Address</br>
         1. provider address</br>
         2. raw provider address )</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/38.jpg"></img></p>

      <p>Selected "Raw Provider Address"  to Un-Link, Dialog confirmation will show up "unlink Selected Raw Provider Address from Sub provider"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/39.jpg"></img></p>
 
    </div>

    <div class="providerManualReviewContent hideContent"> 
      <h1>Standardization -> Provider -> Manual Review </h1>  

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Provider/40.jpg"></img></p>

      <p>More Screens to Add (Data Not Available for Manual Review).</p>
    </div>


    <div class="facilityUnstandardizedContent hideContent"> 
      <h1>Standardization -> Facility -> Unstandardized</h1>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/41.jpg"></img></p>

      <p>In Raw Facility Standardization → "Search for Sub Facility" has ability to search with any of Master Facility Name, Facility Name, etc…</p> 
      <p>Validation Rules - </p>
      <p>1. Master Facility Name - <br>
         2. Facility Name - <br>
         3. Department - <br>
         4. Street Address - Cannot contain special characters other than period(.), comma(,), hyphen(-), ampersand(&), hash(#), fwd slash (/), apostrophe('), Acceptable Characters  Space,0-9,a-z,A-Z,.,-&#/' <br>
         5. City - cannot start with a Space, cannot contain special characters other than dash(-), period(.), space, Acceptable Characters  a-Z,A-Z,space <br>
         6. Zip Code - should only contain digits, Acceptable Characters 0-9 <br>
         7. Phone - should only contain numbers, Acceptable Characters 0-9 </p> 

      <p>In Raw Facility Standardization → Raw Facility Needing Standardization table has three functionalities.</p>
      <p>1. Refresh - will reload the Raw Facility table data <br>
         2. Search - has the ability to search with "Name, Address, etc..."  and " equal, not equal, etc.." <br>
         3. Flag - you can flag as a provider </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/42.jpg"></img></p>
      
      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/43.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/44.jpg"></img></p>   

      <p>If we conduct a Search with Empty fields - That will bring all the Records in Search Results table.</p>

      <p>After conducting Search - "Search For Sub Facility "container is expandable/ Collapsible. The Search Results of a "Facility" are displayed below. </p>

      <p>In Search Results <br>
         1. Red color Represents  - Grand Facility <br>
         2. Green Color Represents - Sub Facility <br>
         3. Brown Color Represents - Raw Facility <br>
         4. Yellow Color Represents - Selected Record </p>

      <p>"Master Facility" - has expand/collapse "+" icon is available. "Master Facility" will have linked to multiple "Sub Facility".</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/45.jpg"></img></p>   

      <p>New Master & Sub Facility - is always enabled without any selection <br>
      (The "Raw Facility" did not find a match with any of the "Master Facility" in the results table. At that time we add a "New Master & Sub Facility" - we can add both Master and Sub Facility Details)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/46.jpg"></img></p> 

      <p>New Sub Facility  - is enabled only when you select any "Master Facility"<br>
      (The "Master Facility" is different from "Sub Facility" in the results table at that time we add a "New Sub Facility" - We cannot edit the "Master Facility Name")</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/47.jpg"></img></p> 

      <p> Standardize - is enabled only when you select any "Master Facility" with "Sub Facility" </p>
      
      <p>(The "Raw Facility" when find a match with the "Standardize Master & Sub Facility" will link)</p>

      <p>We did not find a match for Raw Facility, so we are adding a "New Master & Sub Facility" to that. "Add New Master & Sub Facility and Standardize" Dialog will prompt.</p>
      <p>"*" - are mandatory fields to enter.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/48.jpg"></img></p> 

      <p>After we submit, "Standardize Confirmation" Dialog will appear. It will check whether we want to add New Sub Facility and standardize it to Raw Facility.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/49.jpg"></img></p> 

      <p>After "Add and Link" - New Master & Sub Facility will be created and Raw Facility will be standardize to it.</p>

      <p>From Search results when we select a "Master Facility" (selected record will highlight in yellow color) "New Sub Facility" Button will enabled. When we click on "New Sub Facility" that will bring a dialog "Adding new Sub Facility to Existing Master Facility" - Here we can only Add/Edit the "Sub Facility Details", we cannot Edit Master Facility.</p>

      <p>(Note - Usually the sub Facility Fields are populated automatically due to the "Raw Facility" we selected from the top)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/50.jpg"></img></p> 

      <p>After Submit "Standardize Confirmation" Dialog will appear with "Add and Link" Button.</p>

      <p>Technically we are linking a newly created "Sub Facility" to "Raw Facility". When we hit "Add and Link" The New "Sub Facility" will add a link and standardize it to the Raw Facility.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/51.jpg"></img></p> 

      <p>From Search results when we select a "Master Facility" with "Sub Facility" (selected record will highlight in yellow color) "Standardize" Button will enabled. When we click on "Standardize" that will bring a dialog "Standardize Confirmation" - Here we can Link "Raw Facility" to "Sub Facility" to make it standardize.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/52.jpg"></img></p> 

      <p>When we Link - Raw Facility will be standardized with Sub Facility</p>

      <p>When we see some records under Search Results named as "Unstandardized" - Those are not having any "Master Facility Name". The Sub Facility which don't have any Master Facility called as "Orphan" </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/53.jpg"></img></p> 

      <p>When "Raw Facility" don't find any Match with "Master Facility" and had a match with "Orphans" can be "Standardize"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/54.jpg"></img></p> 

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/55.jpg"></img></p>

      <p>When you hit "Select Facility" It will take you to "Sub Facility without Master" Tab.</p>
      <p>(Note - Usually this "Sub Facility without Master" Tab contains all Orphan's, In our scenario this is showing only one orphan because we came from "Raw Facility Standardization" Tab with a selected Orphan.)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/56.jpg"></img></p>

      <p>Searched for a Master Facility to standardize "Raw Facility Orphan" with "Sub Facility".
      If we don't see the exact match for a "Master Facility", we can still add a "New Master Facility"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/57.jpg"></img></p>

      <p>When we hit "New Master Facility" That will bring up a Dialog for Adding a New Master Facility and Standardize.</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/58.jpg"></img></p>

      <p>After Adding the Master Facility Name, we will get the confirmation for whether we want to "Add New Sub Faciliy and standardize it to raw facility".</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/59.jpg"></img></p>

      <p>When we add a Link - that will create a Master facility and Orphan sub facility and linked to the master facility and raw facility.</p>
      
      <p>After we add a new Master Facility to Raw Facility to make it Standardize, That will take you back to "Raw Facility Standardization" Tab.</p>

      <p>When we Right Click on the any of the Orphan's from below Screen, It will open a dialog for "Raw Facilities Linked to Standard Facility"</p>
      
      <p>(Note - It may or may not contain any matched Raw Facilities in table.)</p>

      <p>(Note - We can only do "Standardize / Add New Master & Sub Facility" for Orphan's)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/60.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/61.jpg"></img></p>

    </div>

    <div class="facilitySearchManageContent hideContent"> 
      <h1>Standardization -> Facility -> Search/Manage </h1>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/62.jpg"></img></p>

      <p>In Standardize, Facility Search Manage → Search Facility contains </p>
      <p>1. Grand Facility - allows only Master Facility search<br>
         2. Standard Facility - allows only Sub Facility search<br>
         3. Raw Facility - allows only Raw Facility search</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/63.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/64.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/65.jpg"></img></p>

      <p>Searched with Facility -"Grand Facility"<br>
      (Note - Facility Search will bring all the records under matched Grand Facility</p>

      <p>1. Grand Facility - When we search with "Grand Facility" - matched Grand Facility records will bring all "Sub Facility and Raw Facility under matched Grand Facility"<br>
         2. Sub Facility - matched Sub Facility records will bring all "Raw Facility under matched Sub Facility along with Grand Facility"<br>
         3. Raw Facility - matched Raw Facility records will bring all "siblings of Raw Facility", with "parent Sub Facility" along with "super Grand Facility" )</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/66.jpg"></img></p>

      <p>Selected work Area Type to "Grand Facility", it will give the check box to select at least 2 Grand Facility to Merge</p>

      <p>(Note - Merge button will enable only when you select 2 Grand Facility to merge.)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/67.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/68.jpg"></img></p>

      <p>After we merge, it will open dialog to select a one grand facility name to keep. </p>
      
      <p>After we hit "Ok", it will show the grand Facilities has been merged. </p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/69.jpg"></img></p>

      <p>Selected work Area Type to "Sub Facility", it will give the check box to select a Sub Facility </p>
      <p>(Note - Merge button will enable only when you select 2 Sub Facility to merge.
         Un-Link button will enable only when you select 1 Sub Facility to Un-Link)</p>
      <p>(Note - Sub Facility Search will bring all the records under matched Sub Facility, Which includes</p>
      <p>1. Grand Facility <br>
         2. Sub Facility <br>
         3. Raw Facility )</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/70.jpg"></img></p>

      <p>Selected the "Sub Facility" to unlink - Confirmation dialog will show "Un-link selected Sub Facility address from Grand Facility xyz"</p>

      <p>After we Un-link, Message will appear that "Facilities have been unlinked"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/71.jpg"></img></p>

      <p>Selected two Sub Facility address to "merge" and selected which Sub Facility to keep for ‘xyz Sub Facility. It will open a dialog confirmation "merge your Sub Facility into ‘Sub Facility selected' ".</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/72.jpg"></img></p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/73.jpg"></img></p>

      <p>Selected work Area Type to "Raw Facility", it will give the check box to select a "Raw Facility" to Un-Link</p>

      <p>(Note - Un-Link button will enable only when you select 1 Raw Facility to Un-Link)</p>

      <p>(Note - Raw Facility Search will bring all the records under matched Raw Facility, which includes
      Raw Facility, Sub Facility which associated to Raw Facility, and Selected Sub Facility which associated to Grand Facility.)</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/74.jpg"></img></p>

      <p>Selected "Raw Facility"  to Un-Link, Dialog confirmation will show up "unlink Selected Raw Facility from Sub Facility"</p>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/75.jpg"></img></p>

    </div>

    <div class="facilityManualReviewContent hideContent"> 
      <h1>Standardization -> Facility -> Manual Review</h1>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/Facility/76.jpg"></img></p>

      <p>More Screens to Add (Data Not Available for Manual Review).</p>
    </div>

    <div class="eHarsFacilityContent hideContent"> 
      <h1>Standardization -> eHars Facility</h1>

      <p><img width="700" height="450" src="${staticPath}/images/userGuide/Standardization/eHars/77.jpg"></img></p>

      <p>More Screens to Add (Data Not Available for Manual Review).</p>
    </div>
    
    <div class="stdOperatingProcedureContent hideContent"> 
    </div>
         
    <div class="consolidatedMergeUnmergeContent hideContent"> 
		<h1>Merging</h1>
		<p>1.	Open NYEHMS</p>
		<p>2.	Open the merge screen by hovering over the Case Data Management tab, then select Merge.</p>
		<p>3.	Select a merge pair by clicking on the <u>REVIEW</u> link.</p>
		<p class="a">a.	You may also search for a specific stateno by clicking on the magnifying glass icon. It is located at the lower left corner of the bottom table.</p> 
		<p class="a">b.	You may also refine your search by origin category, requester comments, follow up, flagged by, and flagged date. </p>
		<p class="a">c.	If the History column is populated with the <img src="${staticPath}/images/icons/history_icon.jpg"></img>  icon, click on the icon to review the history for this stateno. Here, you can read what previous representatives found when reviewing the merge pair. This will likely assist you in your analysis.</p>
		<p>4.	Both statenos will appear side by side. Beginning with the Recent Demo View tab, analyze the data provided in each field. The <img src="${staticPath}/images/icons/add.png"></img>
		icon indicates that more than one value is present in that field. Click on this icon to display all values for this field in each stateno. Scroll to the right and down to display additional fields.</p>
		<p>5.	Continue to the Document Timeline tab. Scroll right to display additional columns, and down to view additional records. You may also click on the document type to display a window with additional data for that record. You may sort by clicking on the header for each column. </p>
		<p class="a">a.	Click the box next to 'HIV Lab Documents only' on each side to display just the laboratory records for each stateno. Then, click the header for Collection Date to sort by date. This makes it easier to see any inconsistencies in the record within that timeline.</p>
		<p class="a">b.	Use the document timeline to look at the labs. Imagine the statenos together. Do the labs fit nicely and flow? Are lab results consistent? Do they have the same address or geographical location and same providers? If the name changes once and they only used one alias before a certain date and another after that date it could be the same person. If the name, date of birth, and/or address changes multiple times throughout, it is almost certainly multiple people. The NYC alias file may help to identify alias names, dates of birth, and SSNs known to a stateno.</p>
		<p>6.	Determine the appropriate resolution for the merge pair and select the corresponding bubble located above the split screen on the Recent Demo View tab. You may merge the pair, or select Not a Match. You will be required to enter supporting comments in a pop-up window if you determine that the pair is not a match. Comments are not necessary or tracked if the pair are merged. Skip simply returns you to the main merging screen and does not resolve the status for the merge pair.</p>
		<p>7.	While combining statenos in NYEHMS, the box that appears to select the stateno you want to keep also displays the eHARS stateno. If the NYEHMS stateno has an eHARS stateno, it will appear below the NYEHMS stateno. If it is different from the NYEHMS stateno, record this number as it will be necessary for merging in eHARS. Once statenos have been combined in NYEHMS, they will need to be combined in eHARS. </p>
		<p>8.	To verify if the stateno is a bad merge in eHARS, enter a stateno and scan the document search results screen for multiple names, Soundex, sex, DOB, or other variables that may indicate more than one person. Alias information should be known from combining in NYEHMS. </p>
		<p>9.	Once the statenos are determined to be the same person and free of bad merges, go to the 'Admin' menu and select 'Document and Case Maintenance'. </p>
	
		<p><b>Steps for 'Document and Case Maintenance':</b></p>
		<p>*	Select New York for the state for both the Source and Target person</p>
		<p>*	Select which stateno to retain. The user should follow the stateno hierarchy</p>
			<pre>o The stateno the user wants to delete should be placed in the Source person stateno</pre>
			<pre>o The stateno the user wants to retain should be placed in the Target person stateno</pre>
		<p>*	Then click Lookup</p>
		<p>*	A list of documents from the Source person will display. All documents should be checked </p>
		<p>*	Then click Transfer. If either the Source or Target person (stateno) does not exist on eHARS, an error message will display </p>
		<p><b>NOTE:</b> Patient identifiers do not display for either stateno during this process. It is very important to ensure statenos are correctly typed into the stateno fields to avoid bad merges.</p> 
	
		<p><b>STATENO HIERARCHY</b><br>STATENO TYPE</p>
		
		<table border=1>
			<tr>
				<th>Stateno</th>
				<th>Type</th>
				<th>Format</th>
			</tr>
			<tr>
				<td>10 million</td>
				<td>New law</td>
				<td>0010######</td>
			</tr>
			<tr>
				<td>9 million</td>
				<td>Old law</td>
				<td>0009######</td>
			</tr>
			<tr>
				<td>8 million</td>
				<td>Old law</td>
				<td>8######</td>
			</tr>
			<tr>
				<td>7 digit </td>
				<td>Old law</td>
				<td>7 digits</td>
			</tr>
			<tr>
				<td>P (begins with)</td>
				<td>Old law</td>
				<td>P######</td>
			</tr>
			<tr>
				<td>NY (begins with)</td>
				<td>Old law</td>
				<td>NY######</td>
			</tr>
			<tr>
				<td>N (begins with)</td>
				<td>Old law</td>
				<td>N#######</td>
			</tr>
		</table>
	
	<p>The hierarchy for statenos is listed in the chart above, from most desirable to least desirable. If the statenos are the same type, the lower number should be selected. It is preferable that the NYEHMS stateno matches the eHARS stateno, if possible (although it is not necessary that they match). When selecting which NYEHMS stateno to keep when merging, select the stateno that will match the eHARS stateno that we are keeping when the pair are merged in eHARS. Obey the hierarchy noted above as well as the eHARS Duplicate Review tab when determining which stateno is kept in eHARS.<br> 
	When using the above hierarchies for combining statenos in eHARS, the presence of a Duplicate Review status of 'Same as' or 'Different Than' with another jurisdiction in eHARS trumps the hierarchy. In such cases, the stateno with the Duplicate Review status should kept in eHARS to avoid showing up on future RIDR lists. Similarly, if merging a stateno associated with a cityno with a stateno associated with an eHARS stateno, be sure to record the cityno in the Duplicate Review tab to minimize future RIDR calls (under Add Document, Adult CRF, Duplicate Review)</p>
	<p>10.	There are several resources available to assist you in making your determination. Consider the following during your analysis:</p>
	 
	<table>
		<tr>
			<td>*	Name			</td>
			<td>*	Date of Death	</td>
			<td>*	Date of Birth		</td>
			<td>*	Provider	</td>
		</tr>
		<tr>
			<td>*	Social Security Number			</td>
			<td>*	CIN (Medicaid Insurance Number)	</td>
			<td>*	Address			</td>
			<td>*	Medical Record Number	</td>
		</tr>
	</table> 
	
	<p>*	Labs and Lab Dates </p>
	 
		<pre>o	Look for corresponding collection dates that:</pre>
		<pre>*	fill in gaps of time</pre>
		<pre>*	pick up where the opposite case appears to have dropped off, accompanied by a change in person or provider address</pre>
		<pre>*	represent the same data (e.g., a CD4 count and percent lab result usually accompany a VL lab result and vice versa) </pre>
		
	<p>*	NYSID or DIN </p>	
	
	<pre>o	Use the following links to research by NYSID or DIN:</pre>
	
	<br>Inmate Search: <font color="blue"><b>http://nysdoccslookup.doccs.ny.gov/GCA00P00/WIQ1/WINQ000</b></font>
	<br>Parolee Search: <font color="blue"><b>http://www.doccs.ny.gov/lookup.html</b></font>
	<br>Victim Notification Network: <font color="blue"><b>https://vinelink.com</b></font>
	
	<p><b>NOTE:</b> If the member has a history of incarceration (denoted by the present of a DIN, NYSID, or correctional facility listed for the patient or provider address), then you should verify through the DOCCS online lookup tools that differing names/DOBs/SSN's are not aliases. Prisoners often have multiple aliases, resulting in completely different names, DOBs, and/or SSN's. If the stateno contains different demographic information but share a DIN or NYSID - they are in fact the same person.</p>
	<pre>*	NYC Alias File data</pre> 
	<pre>o	This is the data available in NYC for each cityno. You may search by cityno, SSN, dob, or name to determine whether any aliases exist. The file is updated semi-monthly and is located at <font color="blue"><b>H:\HAEGroup\Surveillance\NYC Alias File</b></font></pre> 
	
	<p>11.	If you still cannot determine the appropriate resolution, you may flag it for review by a senior staff member. The two alternative resolutions include:</p>
	<p><b>Needs Accurint Search - </b>If a determination cannot be made as to whether the pair should be merged, you may flag it for an Accurint review by a senior staff member. Highlight the line on the merge screen and select Edit (represented by a pencil icon). Then, select the drop-down option 'Needs Accurint Search' in the Follow Up field and select Save.</p>
	<p><b>Needs Secondary Opinion - </b> This option is appropriate when there is not enough information available to facilitate the use of Accurint. Highlight the line on the merge screen and select Edit (represented by a pencil icon). Then, select the drop-down option 'Needs Secondary Opinion' in the Follow Up field and select Save.</p>
	
		<h1>Unmerging</h1>
	<p>1.	Open NYEHMS</p>
	<p>2.	Open the unmerge screen by hovering over the Case Data Management tab, then select Unmerge.</p>
	<p>3.	Select a stateno by clicking anywhere on the line for that stateno.</p>
	<p class="a">a.	You may also search for a specific stateno by clicking on the magnifying glass icon. It is located at the lower left corner of the bottom table. Enter the stateno and click on the Find button.</p>
	<p class="a">b.	You may also refine your search by requester comments, flagged by, flagged date, and history. </p>
	<p class="a">c.	If the History column is populated with the <img src="${staticPath}/images/icons/history_icon.jpg"></img> icon, click on the icon to review the history for this stateno. Here, you can read what previous representatives found when reviewing the stateno. This will likely assist you in your analysis.</p>
	<p>4.	The stateno will appear on the left side of the screen, and a blank search tab will appear on the right. If you know the stateno to which you intend to move documents, enter it on the tab to the right and click Search. You may also enter data in one or more fields to search for a possible match.</p>
	<p>5.	Beginning with the Recent Demo View tab, analyze the data provided in each field. The <img src="${staticPath}/images/icons/add.png"></img> icon indicates that more than one value is present in that field. Click on this icon to display all values for this field in each stateno. Scroll to the right and down to display additional fields. Note whether either stateno also has an eHARS stateno. This will determine any additional steps that may be required.</p>
	<p>6.	Continue to the Document Timeline tab. Scroll right and down to display additional data. You may also click on the document type to display a window with additional data for that record. You may sort by clicking on each column header. </p>
	<p class="a">a.	Click the box next to 'HIV Lab Documents only' on each side to display just the laboratory records for each stateno. Then, click the header for Collection Date to sort by date. This makes it easier to see any inconsistencies in the record within that timeline.</p>
	<p class="a">b.	Use the document timeline to look at the labs. Imagine the statenos together. Do the labs fit nicely and flow? Are lab results consistent? Do they have the same address or geographical location and same providers? If the name changes once and they only used one alias before a certain date and another after that date it could be the same person. If the name, date of birth, and/or address changes multiple times throughout, it is almost certainly multiple people.</p> 
	<p>7.	If you determine that specific documents need to be moved, select the documents by clicking on them. If you accidently select a document, deselect it by clicking on it again. </p>
	<p class="a">a.	If you found the stateno to which the document(s) should be transferred, move the document(s) by clicking on the blue arrow icon located between the two tabs. A pop-up will appear, asking if you are sure that you want to move the document(s). Select yes to continue. Another pop-up will appear, confirming the documents were moved and asking if you want to remove the bad merge flag. If you are certain that the bad merge has been resolved, select OK. Otherwise, select cancel to continue your review of the stateno.</p>
	<p class="a">b.	If you could not find an existing stateno to which you could move the documents, click on the Generate STATENO button located above the stateno screen. This will only be available for use after you have attempted to search for an existing stateno. A pop-up will appear asking if you are sure that you want to create a new stateno. Select yes to continue. Another pop-up will appear, confirming the creation of the new stateno and that the documents were moved. The pop-up will also ask if you want to remove the bad merge flag. If you are certain that the bad merge has been resolved, select OK. Otherwise, select cancel to continue your review of the stateno.</p>
	<p>8.	You will also need to move the documents to the correct stateno in eHARS. If an eHARS stateno does not exist for the Target stateno, then a new stateno will need to be created so the documents can be moved. Enter the following information on the 'eHARS Unmerging' shared document in the shared drive:</p>
	 
		*	eHARS Source stateno<br>
		*	Documents to be moved<br>
		*	NYEHMS Target stateno<br>
		*	Any related comments
	 
	<p class="a">a.	The updated NYEHMS and eHARS statenos will be updated regularly once created by Systems. You will need to monitor the document and move the documents once an eHARS stateno has been assigned for the Target stateno.</p>
	<p class="a">b.	When documents are ready to be moved, go to the Admin menu and select Document and Case Maintenance:</p>
	
	*	Select New York for the state for both the Source and Target statenos. <br>
	*	Then select Lookup.<br>
	*	A list of documents from the Source stateno will display. Each document should be evaluated to determine if it belongs to the Target stateno.  Once you conclude that it belongs to the Target stateno, select the document. After you have selected the documents you want to move, select Transfer.<br> 
	*	Review all statenos involved to ensure that the data in each stateno is correct, and that no residual data from another person remains.
	
	<p>9.	Once the bad merge has been resolved in both NYEHMS and eHARS, or if you determine that the stateno is not a bad merge, return to the Recent Demo View screen. To the right of the stateno number under Person Details you will find radio buttons labeled IS THIS A BAD MERGE. Select no. A pop-up will appear, asking if you are sure that you want to unflag the stateno as a bad merge. Enter supporting comments (not a bad merge, moved documents, etc) and click yes. You will then be returned to the main Unmerge screen.</p>
	
	
	<p><b>Unmerging Bad Death Date:</b><br>
	While in the Unmerge module of NYEHMS:</p>
	
	<p>a.	Search the database for a potential match to move errant documents to. If a match is found, be sure to check eHARS to determine if this document needs to be moved or deleted.</p>
	<p>b.	If there is not a match:
			<pre>i.  Create and new stateno for the death document if there is evidence that the individual is a case (i.e., relevant ICD codes). Since this may be the only document in the stateno, it should also be flagged for a record search.</pre>
			<pre>ii.  The document should be deleted if there is not another match in the registry, and/or the individual does not appear to be a case.  </pre>
	<p>c.	The Social Security Death Index (SSDI) site may be a useful resource to determine which date of death is correct, should multiple dates of death exist. You may need to request access.</p>
	
	<p><b>CLIMS: Merging and unmerging of labs related to Newborn Diagnostic Testing</b></p>
	<p>Laboratory results related to HIV exposed newborn infants are electronically reported by Wadsworth Pediatric Testing Services in NYEHMS.</p>
	<p>Those results will be under the CLIMS banner. It is possible that several infants will be clubbed together as they might share similar dates of birth and gender.</p>
	<p>When reviewing these records for merging or unmerging, the reviewer should pay attention to the field entitled 'RECID'. This is a Wadsworth generated unique identifier to keep track of the infants over the course of their testing. Thus, even though an infant will have several name variations over time between birth and subsequent tests, the RECID field will remain the same.</p> 
	<p>Occasionally, it is possible that two different infants might be assigned the same RECID. While this is a rare event, if the reviewer believes that this is the case, he or she should bring this to the attention of the Perinatal HIV Surveillance Coordinator for further review.</p>
	
	<p><b>Weekly Update Table:</b></p>
	<p>*	Surveillance staff should be updated weekly regarding the overall number of potential duplicate pairs and bad merges. An Excel spreadsheet has been created with a row for each Origin Category and a column for each successive week.</p>
	<p>*	This spreadsheet shall be sent to key BHAE staff including: The Director, Assistant Director, Surveillance Program Manager, Systems Program Manager, Data to Care team lead, and COPS staff.</p>
	<p>*	The spreadsheet may be found here: H:\HAEGroup\Surveillance\Data </p>
	
	
	<p><b>Quick Tips</b></p>
	<p>*	Many useful documents pertaining to merging and unmerging may be found at <font color="blue"><b>H:\HAEGroup\Surveillance\Data\Unmerging</b></font></p>
	
	<p>*	04/06/1957 is an error DOB that is frequently associated with a single document in statenos that belong to people who have been incarcerated. These labs all came from the same laboratory, who likely made a clerical error. It may be a good idea to search for that DOB in NYEHMS and looking at a few statenos to better understand this scenario.</p>
	
	<p>*	The most reliable way to determine if two names are the same person is to use the document timeline, sorted by collection date. If the name changes once, it could be the same person. For example, if they use one alias while in prison and a different one when released, it still may be the same person. If the name changes multiple times, and there is no evidence they were ever in prison, it is almost certainly a bad merge.</p>
	
	<p>*	If you see a non-matching SSN that starts with 100, 200, or 300, establish if the lab was from Interfaith Medical Center. They often make up SSN's. Also, patient data from St Vincent's Hospital is extremely unreliable. Expect variances in data from each of these institutions.</p>
	<p>*	Stateno pairs beginning with 00098, will often feature two statenos with the same last name and SSN, but with a significantly different DOB. This is almost exclusively a mother/child scenario, and the statenos should not be merged. The data overlap is often the result of the child's name and SSN not yet being determined/recorded.</p>
	<p>*	There have been trends where specific document types are incorrect/unreliable. For example, when reviewing dates of death, SSDM documents are the most reliable of the death documents, followed by NDI, DTH, and NYC_DTH. Dates of death found on HIE_DTH, INS, INS_VITAL, NYC_DEMO, and other documents are often incorrect, especially if the dates conflict with other documents. INS_VITAL documents, specifically, may reflect a date of death up to a month after the actual date of death. If you find that the stateno has labs after a documented date of death, and the data otherwise matches, you may have to determine if the offending document needs to be moved to a different stateno, or if it needs to be edited. DIN ID's are particularly susceptible to clerical errors. Carefully review each stateno's DIN history to determine if there is a DIN error, or if a merge/unmerge of records is necessary.</p>
	<p>*	Occasionally, two statenos will match on name and DOB, but will have different SSN's. If these SSN's are only associated with non-laboratory documents (AIRS/ADAP/NYC_DEMO), they are likely clerical errors. You may need to edit the document to prevent future bad matches that may be based on this data.</p>
	
	<p>*	This is the most reliable and efficient method to ensure that all of the intended documents (and ONLY the intended documents) are transferred. Bring up the source stateno, then copy every line starting with the Person View record, all the way down to the last record on the page. Paste the data into the template, which is already pre-sorted by column. You may then filter/sort by name, DOB, DOD, etc. Then, copy the document ID to be moved and paste it into the Document UID field in eHARS.</p>
	  
	<p>*	You will frequently see certain addresses when reviewing statenos; these are often correctional, rehabilitation, and medical facilities. Some prison addresses that frequently appear include (full list at: <font color="blue"><b>http://www.doccs.ny.gov/faclist.html):</b></font></p>
	 
	o	125 WHITE ST, NEW YORK<br>
	o	1 HALLECK ST, BRONX<br>
	o	1010-1818 HAZEN ST, E ELMHURST<br>
	o	RED SCHOOLHOUSE RD, FISHKILL
	 
	<p>*	Don't assume that the data presented in the eHars Duplicate Review history is accurate when determining whether stateno pairs should be merged. While it is important to review the information recorded, you must also consider when and how the data was derived. Many changes to the merging and unmerging process have occurred over the years, and the respective stateno's themselves may have been merged/unmerged several times since the duplicate review information was input.</p> 
	<p>*	Older labs may not have the provider field entered. However, the provider information may be entered in the Facility Details section of the lab document. This field is not shown on the document timeline. This method may help to determine if two statenos have had the same provider.</p>
	<p>*	Both a CD4 and VL are taken during a provider visit. Sometimes one of these labs may have been entered with incomplete information. Reviewing the collection date and provider may help to determine if labs from two statenos belong together.</p>
    </div>     
    
    <div class="clericalReviewProtocolContent hideContent"> 
	<p><b>Background:</b> Incoming labs, provider reports, or supplemental registry match documents are imported into the NYEHMS Clerical Review database for staff to determine whether the incoming data matches to a case already in the system. The data is imported on a daily basis and should be resolved accurately and in a timely manner.</p>
	  
	<p>Statenos flagged for review will fall into one of the following categories:</p>
		<pre>Match</pre>
		<pre>Not a Match</pre>
		<pre>Skip</pre>
		
	<p><b>Match -</b> look for similarities or exact matches in the following data elements:</p>
		<pre>First and Last Name</pre>
		<pre>Date of Birth</pre>
		<pre>SSN</pre>
		<pre>Person Address (If address does not match you can check to see borough)</pre>
		<pre>Date of Death</pre>
		<pre>Provider</pre>
		<pre>Labs and lab dates -</pre>
		<pre>o	Look for corresponding lab collection dates that fill in gaps of time </pre>
	
	<p>Collection dates that pick up where the opposite case appears to have dropped off (or vice versa) accompanied by a change in person or provider address</p>
		<pre>o	Are on the same day (ex: a CD4 count + percent lab result usually accompany a VL lab result and vice versa)</pre>
		<pre>o	Three (3) strong data points are needed to match </pre>
		<pre>NYSID or DIN -</pre> 
			<pre>o	Check <font color="blue"><b>http://nysdoccslookup.doccs.ny.gov/GCA00P00/WIQ1/WINQ000</b></font> or <font color="blue"><b>https://www.parole.ny.gov/lookup.html</b></font> for additional information, name and DOB aliases may be available to view</pre>
			<pre>o	DIN numbers follow a person for life</pre>
		<pre>Medical Record Number</pre>
		<pre>CIN</pre> 
	
	**Tip: the general rule of thumb try to find at least 3 of the above same as, but use your gut instinct. If something feels off, get a second opinion. If it's an unusual name and the date of birth match go ahead and merge, etc.  Make allowances for obvious typos, such as switched first and last names, 4 versus 9, etc.<br><br> 
	
	<p><b>Not a Match -</b> Select this option when it's clearly not a match. Often you'll see the same name but different dates of birth, sometimes they are family members, or only the last name matches, partial SSN will match, etc. *Note* If one or both members of a pair have a history of incarceration (denoted by the 
	presence of a DIN, NYSID, or correctional facility listed for either the patient or provider address) then you should verify through the DOCCS online lookup tools that differing names/DOBs/SSNs are not really aliases. Prisoners often have multiple aliases, resulting in completely different Names, DOBs, and/or SSNs. If two seemingly different cases share a DIN or NYSID-they are in fact the same person and should be matched in the registry. Likewise, Transgender individuals may have two different names and genders but are in fact the same person.</p>
	
	<p><b>Skip</b> - If you can't decide on a merge pair or are feeling uncomfortable making a decision, you can skip the record to have another staff member review the merge pair to make a decision.  Also, if you're having trouble with the view screen or the data isn't loading, skipping to the next record will often correct this problem.</p> 
	
	<p><b>Bad Merge -</b> Leave this merge pair alone if you see that one of the statenos has been flagged <font color="red">Bad Merge</font>, this happens when multiple records from two different people have been previously merged erroneously.  These statenos need to be separated before we can merge additional statenos with it.  If you see a stateno that looks like a bad merge you can flag it as such for review.</p>
	
	<p><b>Multiple Records -</b> Sometimes an incoming document will match to multiple records; when this happens, it is an indication that the two statenos that already exist in our database need to be merged.  You can't do this in the Clerical Review Screen.  Before merging the incoming document, keep track of the two statenos that need to be merged in NYEHMS on a separate Word Document, Excel, or scrap paper (whichever you prefer), and after merging the incoming document into one of the statenos, go into the Merge module within the Case Data Management tab to merge the other two statenos.</p>
    </div>     

    <div class="glossaryContent hideContent"> 
<p><b>Analyte:</b> A substance from the body that is undergoing analysis, often referred to as a 'test,' i.e., HIV antibodies, HIV-1/2 antibodies, HIV-1/2 antigens and antibodies.</p>

<p><b>Differentiating:</b> Test that detects and differentiates HIV-1 and HIV-2.</p>  
                          <pre>§  Bio-Plex 2200 HIV Ag-Ab Assay can detect and differentiate HIV-1 p24 antigen, HIV-1 antibody, and HIV-2 antibody.</pre>
                          <pre>§  Multispot HIV-1/HIV-2 Rapid Immunoassay can detect and differentiate HIV-1 antibody and HIV-2 antibody.</pre>
                          <pre>§  Geenius HIV 1/2 Supplemental Assay can detect and differentiate HIV-1 antibody and HIV-2 antibody.</pre>

<p><b>Immunoassay:</b> A biochemical test that measures the presence or concentration of a macromolecule or a small molecule in a solution through the use of an antibody or immunoglobulin.</p>

<p><b>Indeterminate:</b> A test was run but did not provide a clear negative or positive result.  People with indeterminate HIV test results can be HIV infected and in the process of seroconverting, a time during which a 
                          HIV test would show a result somewhere between negative and positive.  Also, for many reasons (other viral infections, or nonspecific antibodies in the blood), some people can be HIV uninfected 
                          and have an indeterminate test.  Specimens with an indeterminate on the HIV-1/HIV-2 antibody differentiation immunoassay should be tested with an FDA-approved HIV-1 nucleic acid test (NAT).</p> 

<p><b>Qualitative RNA:</b> The APTIMA HIV-1 RNA Qualitative Assay is an in vitro (i.e., outside normal biological context) nucleic acid test (NAT) for the detection of HIV-1 in human plasma and serum. It is intended for 
                             use as an aid in the diagnosis of HIV-1 infection, including acute or primary infection.  During the APTIMA assay, viral RNA is released and captured by HIV-1 specific oligonucleotides. The HIV-
                             1 RNA is amplified by transcription-mediated amplification and detected by complementary chemiluminescent nucleic acid probes.  The APTIMA assay produces a qualitative result and is 
                             reported as detected or not detected.</p> 

<p><b>Quantitative RNA:</b> The HIV-1 RNA, Quantitative, Real-Time PCR (HIV Viral Load) test is intended for use in conjunction with clinical presentation and other laboratory markers of disease progress for the clinical 
                               management of HIV-1 infected patients. The test can be used to assess patient prognosis by measuring the baseline HIV-1 RNA level or to monitor the effects of antiretroviral therapy by 
                               measuring changes in EDTA plasma HIV-1 RNA levels during the course of antiretroviral treatment.</p>

<p><b>Screening:</b> Screening tests are laboratory tests that help to identify people with increased risk for a condition or disease before they have symptoms or even realize they may be at risk so that preventive 
                    measures can be taken.  In terms of the HIV Diagnostic Algorithm, the first step of the algorithm is the screening test (recommended - HIV-1/2 ag/ab combo immunoassay or approved HIV-1/2 ab 
                    immunoassay).  A positive screening test does not confirm a diagnosis and additional testing is needed.</p>

<p><b>Undifferentiated:</b> Result did not differentiate between HIV-1 and HIV-2.  This result requires additional testing.</p>

<p><b>VL = non-zero numeric result:</b> The HIV viral load is the number of copies of the human immunodeficiency virus in the blood and other parts of the body.  Because the viral load measures the amount of virus in 
                                                    the body, a low viral load number is preferred.  The non-zero numeric result is the number (be sure to look at the number labels because viral load can be expressed in terms of 
                                                    logs).</p>

<p><b>Western blot:</b> A qualitative assay for the detection of HIV-1 antibodies in human serum, plasma and dried blood spots.  The Western blot detects IgG antibodies produced against 
                        specific HIV-1 proteins.  The Western blot is no longer recommended for HIV confirmation because it can produce false negative or indeterminate results on specimens that are reactive by the 
                        currently recommended screening tests and it misclassifies most HIV-2 infections. However, the Western blot continues to be useful in certain circumstances.</p>

    </div>
    
  </div>  

</div>
<script type="text/javascript">
$(document).ready(function(){
  var enableElementId = '<c:out value="${param.id}" />';
  if(enableElementId == 'adminPage'){
    var id = 'SearchManageProfile';  
    /* $('li#administration_tab label').click(); 
    $('li#standardization_tab').click(); */
    $('li#administration_tab label').click();
    $('#SearchManageProfile').click(); 
  } else if (enableElementId == 'newUserPage'){
      var id = 'CreateNewUser';
      $('li#administration_tab label').click();
      $('#CreateNewUser').click(); 
  } else if (enableElementId == 'systemMessagingPage'){
      var id = 'SystemMessageManage';
      $('li#administration_tab label').click();
      $('#SystemMessageManage').click(); 
  } else if (enableElementId == 'providerStdz'){
      var id = 'ProviderUnstandardized';
      $('li#standardization_tab label').click();
      $('#ProviderUnstandardized').click();
  } else if (enableElementId == 'providerSearch'){
      var id = 'ProviderSearchManage';
     $('li#standardization_tab label').click();
      $('#ProviderSearchManage').click();
  } else if (enableElementId == 'newPageRawStdProvider'){
      var id = 'ProviderManualReview';
      $('li#standardization_tab label').click();
      $('#ProviderManualReview').click();
  } else if (enableElementId == 'unStandardizeFacility'){
      var id = 'FacilityUnstandardized';
      $('li#standardization_tab label').click();
      $('#FacilityUnstandardized').click();
  } else if (enableElementId == 'mainSearch'){
      var id = 'FacilitySearchManage';
      $('li#standardization_tab label').click();
      $('#FacilitySearchManage').click();
  } else if (enableElementId == 'newPageRawStdFacility'){
      var id = 'FacilityManualReview';
      $('li#standardization_tab label').click();
      $('#FacilityManualReview').click();
  }       
});
  
</script>