<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- This is JSP file for Message Box-->
<html>
    <head>
        <title>Message</title>
        <%@ include file="../common/common_includes.jsp" %> 
        <%@ include file="../common/commonStylesIncludes.html" %> 
        <%@ include file="../common/MainMenuIncludes.jsp" %> 
        <link href="${staticPath}/css/main.css" rel="stylesheet" type="text/css" />
        <link href="${staticPath}/css/common.css" rel="stylesheet" type="text/css" />
        <link href="${staticPath}/css/message/message.css?v=${buildDate}" rel="stylesheet" type="text/css" />

        <link href="${staticPath}/css/jquery.tagit.css" rel="stylesheet" type="text/css">

        
    </head>

    <body bgcolor="#e4e4e4" background="${staticPath}/images/NYEHMSVirusBackground.png">

        <div id="floatContent">
            <%@ include file="../common/Menu.jsp" %>
            <div id="messageContentDiv">
                <div id="tabs">
                    <ul id="Tabs1">
                        <li id="sCriteriaTab"><a href="#messageViewTab">Message</a></li>
                    </ul>

                    <div id="messageViewTab" class="height325">
                        <div id="personViewSearchFields">
                            <div id="accordion">
                                <h3>Secured Message box</h3>
                                <div id="mesChatContent">
                                    <div id="messageMenuTabs" class="menuLeft">
                                        <ul class="tabsMessage group">
                                            <li class="active" id="RECIEVER"><a href="#messageMenuInbox">Inbox</a></li> 
                                            <li id="SENDER"><a href="#messageMenuSent">Sent</a></li> 
                                        </ul>

                                        <!-- <div id="messageMenuInbox" class="wrapper1 displayList"> 

                                        </div>
                                        <div id="messageMenuSent" class="wrapper1 noDisplayList"> 

                                        </div>
                                         -->
                                         
                                         <div id="messagesBox" class="wrapper1 displayList">
                                         
                                         
                                         </div>
                                    </div>  

                                    <div class="bodyRight wrapper"> 
                                        <div class="pB10"> 
                                            <span><img class="messImg1 actionIcon" id="newMessageImg" src="${staticPath}/images/icons/new_message.jpg" onclick="newMessage()"/></span>
                                            <span><img class="messImg1 actionIcon" src="${staticPath}/images/icons/reply_message.jpg"  onclick="replyMessage()"/> </span>
                                            <span><img class="messImg1 actionIcon" src="${staticPath}/images/icons/forward_message.png" onclick="forwardMessage()"/> </span>
                                            <span><img class="messImg1 actionIcon" src="${staticPath}/images/icons/delete_message.png"  onclick="deleteMessage()"/> </span>
                                            <span><img class="messImg1 actionIcon" src="${staticPath}/images/icons/refresh_message.jpg"  onclick="refreshData()"/> </span>
                                            <br>
                                            <span id="newMessageImg" class="messImgLabel1">New</span>
                                            <span class="messImgLabel2">Reply</span>
                                            <span class="messImgLabel1">Forward</span>
                                            <span class="messImgLabel1">Delete</span>
                                            <span class="messImgLabel1">Refresh</span>
                                            <br>
                                        </div> 

                                        <div class="bB1S"> 
                                            <span class="pR10"><img class="messImg2" src="${staticPath}/images/icons/person_message.png"/></span> <textarea rows="1" cols="128" class="senderField" id="sender" readonly></textarea> 
                                        </div>

                                        <textarea rows="19" cols="128" id="mainMsgContent" class="contentWrapper"> 
                                            
                                        </textarea>
                                    </div>
                                </div>
                            </div>    
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="newMessContent" class="displayNone">
            <div id='messageComposer' title='Message'>
            	<div id="showMessageComposeErrors" class="transactionMessage"><span class="message"></span></div>
                <div id="message" style="margin-top: 20px;">
                    <div class="ui-widget bB1S" style="padding:5px">    
                        <!-- <span class="pR10">
                            <img class="messImg2" src="${staticPath}/images/icons/person_message.png"/>
                        </span> 
                        <input id="Reciepient_Details" name="Reciepient Details" style="width:690px"/> -->
                        <div id="wrapper"> 
                            <div id="content" style="width:100%">  
                                <form>
                                    <span class="pR10" style="width:5%;">
                                        <img class="messImg2" src="${staticPath}/images/icons/person_message.png"/>
                                    </span>  
                                    <span  style="width:95%;float:right">
                                        <ul id="eventTags">
                                        </ul>
                                    </span>    
                                </form>
                                <div id="events_container"></div>
                            </div>
                        </div>
                    </div>
                    <textarea id="messageBodyTextArea" rows="12" cols="137" ></textarea>
                    <div class="pT20">
                        <center>
                            <a class="a_demo_save actionIcon cW" tabindex="10" onclick="sendMessage()"> Send </a>
                        </center>    
                    </div>
                </div>
            </div>    
        </div>

        <!-- <div id="newMessContent" class="displayNone">
            <div class="bB1S"> 
                <span class="pR10"><img class="messImg2" src="${staticPath}/images/icons/person_message.png"/></span> <textarea rows="1" cols="129" class="senderField" id="DialogSender"></textarea> 
            </div>
            <textarea id="messageBodyTextArea" rows="12" cols="137" > </textarea>
            <div class="pT20">
                <center>
                    <a class="a_demo_save actionIcon cW" tabindex="10" onclick="sendMessage()"> Send </a>
                </center>    
            </div>
        </div> --> 

        <!-- <div id="replyMessContent" class="displayNone">
            <div class="bB1S"> 
                <span class="pR10"><img class="messImg2" src="${staticPath}/images/icons/person_message.png"/></span> <textarea rows="1" cols="129" class="senderField" id="DialogSender"></textarea> 
            </div>
            <textarea id="messageBodyTextArea" rows="12" cols="137" > </textarea>
            <div class="pT20">
                <center>
                    <a class="a_demo_save actionIcon cW" tabindex="10" onclick="sendMessage()"> Send </a>
                </center>    
            </div>
        </div> -->

        <!-- <div id="forwardMessContent" class="displayNone">
            <div class="bB1S"> 
                <span class="pR10"><img class="messImg2" src="${staticPath}/images/icons/person_message.png"/></span> <textarea rows="1" cols="129" class="senderField" id="DialogSender"></textarea> 
            </div>
            <textarea rows="12" id="messageBodyTextArea" cols="137" > </textarea>
            <div class="pT20">
                <center>
                    <a class="a_demo_save actionIcon cW" tabindex="10" onclick="sendMessage()"> Send </a>
                </center>    
            </div>
        </div> -->

        <%@ include file="../common/commonScriptsIncludes.html"%>
        <script src="${staticPath}/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
        <script src="${staticPath}/js/messageBox/messageBox.js?v=${buildDate}"></script>
    </body>
</html>