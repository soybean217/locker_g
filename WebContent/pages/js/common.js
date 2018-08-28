    new_element = document.createElement("script");
    new_element.setAttribute("type", "text/javascript");
    new_element.setAttribute("src", "validator.js");

    // all rules selected
	function selectAll(checkbox) {
		if(checkbox != null && checkbox.length > 0) {
			for(var i = 0;i < checkbox.length;i++) {
				checkbox[i].checked = true;
			}
		}		
	}
	// selected rules reversed
	function reverse(checkbox) {		
		if(checkbox != null && checkbox.length > 0) {
			for(var i = 0;i < checkbox.length;i++) {
				if(checkbox[i].checked) {
					checkbox[i].checked = false;
				} else {
					checkbox[i].checked = true;
				}
			}
		}
	}
	
	function trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	String.prototype.trim = function () {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	
	//全选，或全不选checkboxes
	function reverseAllCheckboxes(main, ids) {
	    if (ids != null) {
	    	if (ids.length == undefined) {
	    		if (main.checked == true) {
	                ids.checked = true;  
	            } else {
	                ids.checked = false;
	            }
	    	} else {
	    		for (var i = 0; i < ids.length; i++) {
		        	if (main.checked == true) {
		                ids[i].checked = true;  
		            } else {
		                ids[i].checked = false;
		            }
		        }
	    	}
	    }
	}
	
	//点击首页、末页、上页、下页
	function doCurrentPage(type) {
        var cur = document.input.currentPage.value;
        var tot = document.input.totalPages.value;
        if (type == 'first') {
        	document.input.currentPage.value = 1;
        } else if (type == 'end') {
        	document.input.currentPage.value = tot;
        } else if (type == 'previous') {
        	document.input.currentPage.value = --cur;
        } else if (type == 'next') {
        	document.input.currentPage.value = ++cur;
        }
        document.input.submit();
        return;
	}
	
	//点击首页、末页、上页、下页
	function toCurrentPage(type) {
        var cur = document.input.currentPage.value;
        var tot = document.input.totalPages.value;
        if (type == 'first') {
        	document.input.currentPage.value = 1;
        } else if (type == 'end') {
        	document.input.currentPage.value = tot;
        } else if (type == 'previous') {
        	document.input.currentPage.value = --cur;
        } else if (type == 'next') {
        	document.input.currentPage.value = ++cur;
        }
        document.input.submit();
        return;
	}
	
	//选择每页行数
    function changeLinesPerPage(pageNum) {
        document.input.currentPage.value = 1;
        document.input.pageNum.value = pageNum;
        document.input.submit();
        return;
    }
