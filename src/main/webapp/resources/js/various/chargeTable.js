/*
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* ========================================================================
 * Charge Options for datatables
 * ========================================================================

 * ======================================================================== */

var ln = window.navigator.language||navigator.browserLanguage;
if(ln == 'es'){
	$(document).ready(function() {
		   $('#customTable').dataTable( {
		       "language": {
		           "sUrl": "/members/resources/json/datatables/Spanish.json"
		       },
		       "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
		   } );
		   $('#customTable2').dataTable( {
		       "language": {
		    	   "sUrl": "/members/resources/json/datatables/Spanish.json"
		       },
		       "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
		   } );
	} );
} else if(ln == 'gl'){
		$(document).ready(function() {
			   $('#customTable').dataTable( {
			       "language": {
			    	   "sUrl": "/members/resources/json/datatables/Galician.json"
			       },
			       "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
			   } );
			   $('#customTable2').dataTable( {
			       "language": {
			    	   "sUrl": "/members/resources/json/datatables/Galician.json"
			       },
			       "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
			   } );
		} );
// By defect English	
} else {
	$(document).ready(function() {
	    $('#customTable').dataTable( {
	        "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
	    } );
	    $('#customTable2').dataTable( {
	        "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
	    } );
	} );
}