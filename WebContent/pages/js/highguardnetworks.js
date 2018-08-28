String.prototype.Trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};


  function toggle( element )
  {
    if( document.getElementById( element ).style.display=='none' )
    {
      document.getElementById( element ).style.display = '';
    }
    else
    {
      document.getElementById( element ).style.display = 'none';
    }
  }

  function toggleborder( obj )
  {
    var className = document.getElementById( obj ).className;

    document.getElementById(obj).className = (className.indexOf( 'noborder' ) == -1) ? 'noborder' + className : className.replace( 'noborder', '' );
  }
  
  function togglebottomborder( obj )
  {
    var className = document.getElementById( obj ).className;

    document.getElementById(obj).className = (className.indexOf( 'nobottomborder' ) == -1) ? 'nobottomborder' + className : className.replace( 'nobottomborder', '' );
  }

  function toggleImg( img, suff0, suff1 )
  {
    var src = img.src;
    img.src = src.indexOf(suff0) > -1 ? src.replace( suff0, suff1 ) : src.replace( suff1, suff0 );
  }
  
/* sboggan, Aug 02 */
function selectOptionValue( selObj, value )
{ 
for ( index = 0; index < selObj.length; index++ )  
	{    
		if ( selObj.options[ index ].value == value )    
		{      
			selObj.options[ index ].selected = true;      
			return;    
		}  
	}
}
function mOvr( src, clrOver ) 
{ 
  if ( !src.contains( event.fromElement ) )
  { 
    src.style.cursor = 'pointer'; 
    src.bgColor = clrOver;
  }
}

function mOut( src, clrIn ) 
{ 
  if ( !src.contains( event.toElement ) )
  {
    src.style.cursor = 'default';
    src.bgColor = clrIn;
  }
} 

function mClk( src ) 
{
  if( event.srcElement.tagName == 'TD' )
  {
    src.children.tags( 'A' )[ 0 ].click();
  } 
}

function submitCommand( commandName )
{
  document.input.command.value = commandName;
  document.input.submit();
}

function submitServiceCommand( serviceName, commandName )
{
  document.input.command.value = commandName;
  document.input.serviceName.value = serviceName;
  document.input.submit();
}

function toggleForm( formType )
{
   webForm = document.getElementById( "web" )
   avpnForm = document.getElementById( "avpn" )

   window.focus()

   if ( formType == 'web' )
   {
     webForm.style.display = ""
     avpnForm.style.display = "none"
   }
   else
   {
     webForm.style.display = "none"
     avpnForm.style.display = ""
   }
}

function hideShowAll()
{
  if( !document.getElementById | document.all )
  {
    alert("This browser does not support collapsible categories")
    return
  }
  else
  {
    var bodys = document.getElementsByTagName( 'tbody' );
    var displayed = false;

    for ( i = 0; i < bodys.length; i++ )
    {
      if ( bodys[i].id.length > 1 && bodys[i].style.display == "" )
        displayed = true;
    }

    for ( i = 0; i < bodys.length; i++ )
    {
      var sectionName = bodys[i].id.substring( 0, bodys[i].id.length - 4 );
      if ( sectionName.length > 1 )
      setVisible( sectionName, displayed );
    }
  }

  return false;
}

function setVisible(categoryID, hide )
{
    if (!document.getElementById|document.all)
    {
        alert("This browser does not support collapsible categories")
        return
    }
    else
    {
        catHeader = document.getElementById( categoryID + "Header");
        catBody = document.getElementById( categoryID + "Body");
        catImg = document.getElementById( categoryID + "Img");
    }

    window.focus();

    if ( catBody.style.display == "none" && ! hide )
    {
        catHeader.style.backgroundColor='#99a1bf';
        catBody.style.display = "";
        catImg.src="images/btn_banner_up.gif";
    }
    else if ( catBody.style.display == "" && hide )
    {
        catHeader.style.backgroundColor='#ccd0e1';
        catBody.style.display = "none";
        catImg.src="images/btn_banner_down.gif";
    }
}

function hideShow(categoryID)
{
    if (!document.getElementById|document.all)
    {
        alert("This browser does not support collapsible categories")
        return
    }
    else
    {
        catHeader = document.getElementById( categoryID + "Header");
        catBody = document.getElementById( categoryID + "Body");
        catImg = document.getElementById( categoryID + "Img");
    }

    window.focus();

    if ( catBody.style.display == "none" )
    {
        catHeader.style.backgroundColor='#99a1bf';
        catBody.style.display = "";
        catImg.src="images/btn_banner_up.gif";
    }
    else
    {
        catHeader.style.backgroundColor='#ccd0e1';
        catBody.style.display = "none";
        catImg.src="images/btn_banner_down.gif";
    }
}

function hide( id )
{
  if ( !document.getElementById | document.all )
  {
    return;
  }
  else
  {
    body = document.getElementById( id );

    if ( body )
    {
      body.style.display = "none";
    }
  }
}
function show( id )
{ 

	if ( !document.getElementById | document.all ) 
		{    
		return;  
		} 
	else 
		{    
		body = document.getElementById( id ); 
		if ( body )   
			{      
			body.style.display = "";  
			}
		}
}
function transferValues( currForm, fieldName, url )
{
  
  var importValues = "";
  var currValues = "";
  var selectedItems = currForm.elements["selectedItems"];
  var address = "";
  var name = "";


  if( ! selectedItems.length && selectedItems )
  {
    if( selectedItems.checked )
    {
      var item = escapeSemicolons( selectedItems.value );

      if ( currValues.length > 0 )
        currValues = currValues + "; " + item;
      else
        currValues = item;
    }
  }
  else if ( selectedItems )
  {
    for( i=0; i < selectedItems.length; i++ )
    {
      if( selectedItems[i].checked )
      {
        var item = escapeSemicolons( selectedItems[i].value );

        if ( currValues.length > 0 )
          currValues = currValues + "; " + item;
        else
          currValues = item;

      }
    }
  }
  if( currValues && currValues.length > 0 )
  {
  		window.opener.document.forms["input"].elements[fieldName].value = currValues;
	    /*	importValues = window.opener.document.forms["input"].elements[fieldName].value;

    		if(( importValues.length > 0) && (fieldName != "addDestResourceName") )
      			window.opener.document.forms["input"].elements[fieldName].value = currValues + "; " + importValues;
    		else
      			window.opener.document.forms["input"].elements[fieldName].value = currValues;
  		*/
  } else {
  		window.opener.document.forms["input"].elements[fieldName].value = "";
  }

	if (url && url.length > 0) {
		if ( currValues && currValues.length > 0 )
			url += currValues;
		document.input.action = url;
	}
  if ( document.input.showType.disabled )
  {
    document.input.showType.disabled = false;
  }
}

function escapeSemicolons( value )
{
  var item = new String( value );

  if ( item.indexOf( ";" ) >= 0 )
  {
    var escapedItem = new String( "" ); 

    for ( i = 0; i < item.length; i++ )
    {
      if ( item.charAt( i ) == ";" )
      {
        escapedItem = escapedItem + "\\";
      }

      escapedItem = escapedItem + item.charAt( i );
    }

    item = escapedItem;
  }

  return item;
}

function disableOnSelect( selectedValue )
{
 
  if ( selectedValue == "ldap") {

    document.input.dirType1.checked = true;
    document.input.dirType2.checked = false;
    document.input.dirType3.checked = false;

    document.input.credType1.disabled = false;
    document.input.credType1.checked = true;

    document.input.credType2.disabled = true;
    document.input.credType2.checked = false;

    document.input.credType3.disabled = true;
    document.input.credType3.checked = false;
    document.input.credType4.disabled = false;
    document.input.credType4.checked = false;    

  } else if ( selectedValue == "radius" ) {

    document.input.credType1.disabled = false;
    document.input.credType1.checked = true;
    document.input.credType2.disabled = false;
    document.input.credType2.checked = false;
    document.input.credType3.disabled = false;
	document.input.credType8.disabled = false;
	document.input.credType8.disabled = false;
    document.input.credType3.checked = false;
    document.input.credType4.disabled = true;
    document.input.credType4.checked = false;    

  } else if (selectedValue == "local" ) {

    document.input.credType1.disabled = false;
    document.input.credType1.checked = true;
    document.input.credType2.disabled = true;
    document.input.credType2.checked = false;
    document.input.credType3.disabled = true;
    document.input.credType3.checked = false;
    document.input.credType4.disabled = true;
    document.input.credType4.checked = false;
  }
}

function openHelp( langdir, id )
{
  var path = "help/" + langdir + "/wwhelp.htm";

  if ( id != "" )
  {
   // path = path + "?single=true&context=HighGuardNetworks_Help&topic=" + id;
 	path = path + "#" + id;

  } 

  window.open(path,"HGHelp","width=800,height=600,scrollbars=yes,resizable=yes");
}

function toggleHighlight( element, normalColor, highlightColor, unlessColor )
{
  var child = element.nextSibling;

  while( child != null )
  {
    if( child.className == unlessColor )
    {
      return;
    }
    if ( normalColor == child.className )
    {
      child.className = highlightColor;
    }
    else
    {
      child.className = normalColor;
    }

    child = child.nextSibling;
  }

  if( document.input.browsewinelem != null )
  {
    toggleSortType();
  }
}

function checkForPopUp()
{
  /*
  var parentWindow = window.parent;

  if( parentWindow != null )
  {
    if( window.opener != null )
    {
      var form = window.opener.document.forms["input"];

      if ( form != null )
      {
        form.submit();

        window.close();
      }
    }
  }
  */
}

function setActiveModule( id )
{
  document.input.activeModule.value = id;
  document.input.submit();
}

function toggleUDPOptions()
{
  if ( document.input.udp.checked )
  {
    document.input.encryptUDP1.disabled = false;
    document.input.encryptUDP1.checked = true;
    document.input.encryptUDP2.disabled = false;
    document.input.encryptUDP3.disabled = false;
  } 
  else 
  {
    document.input.encryptUDP1.disabled = true;
    document.input.encryptUDP1.checked = false;
    document.input.encryptUDP2.disabled = true;
    document.input.encryptUDP2.checked = false;
    document.input.encryptUDP3.disabled = true;
    document.input.encryptUDP3.checked = false;
  }
}

function toggleCommandOptions( id )
{
  if ( id == 'any' )
  {
    document.input.bind.disabled = true;
    document.input.bind.checked = false;
    document.input.connect.disabled = true;
    document.input.connect.checked = false;
    document.input.ping.disabled = true;
    document.input.ping.checked = false;
    document.input.traceroute.disabled = true;
    document.input.traceroute.checked = false;
    document.input.udp.disabled = true;
    document.input.udp.checked = false;
    document.input.encryptUDP1.disabled = true;
    document.input.encryptUDP1.checked = false;
    document.input.encryptUDP2.disabled = true;
    document.input.encryptUDP2.checked = false;
    document.input.encryptUDP3.disabled = true;
    document.input.encryptUDP3.checked = false; 
  }
  else if ( id == 'selected' )
  {
    document.input.bind.disabled = false;
    document.input.connect.disabled = false;
    document.input.ping.disabled = false;
    document.input.traceroute.disabled = false;
    document.input.udp.disabled = false;
  }
}

function toggleSortType()
{
  try{
  
       var selectedItems = document.input.elements[ "selectedItems" ];

  if ( ! selectedItems.length && selectedItems )
  {
    if ( selectedItems.checked )
    {
      document.input.showType.disabled = true;
      return;
    } 
  }
  else if( selectedItems )
  {
    for ( i = 0; i < selectedItems.length; i++ )
    {
      if ( selectedItems[i].checked )
      {
        document.input.showType.disabled = true;
        return;
      }
    }
  }

  if ( document.input.showType.disabled )
  {
    document.input.showType.disabled = false;
  }
  
     
  }catch(e){
   
  }  

}
