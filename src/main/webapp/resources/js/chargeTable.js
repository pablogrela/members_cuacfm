/* ========================================================================
 * Charge Options for datatables
 * ========================================================================

 * ======================================================================== */

var ln = x=window.navigator.language||navigator.browserLanguage;
if(ln == 'es'){
	$(document).ready(function() {
		   $('#customTable').dataTable( {
		       "language": {
		           "url": "//cdn.datatables.net/plug-ins/f2c75b7247b/i18n/Spanish.json"
		       },
		       "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
		   } );
	} );
// By defect English	
}else{
	$(document).ready(function() {
	    $('#customTable').dataTable( {
	        "aoColumnDefs" : [ { "bSortable" : false, "aTargets" : [ "no-sort" ] } ]
	    } );
	} );
}