/*
 * 	This file is part of DicomFlow.
 * 
 * 	DicomFlow is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 * 
 * 	This program is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 	GNU General Public License for more details.
 * 
 * 	You should have received a copy of the GNU General Public License
 * 	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package br.ufpb.dicomflow.integrationAPI.message.xml;

public class ErrorCodes {
	
	public static final String mail_error = "1";
	public static final String mail_receipt_error = "1.1";
	public static final String mail_receipt_failed = "1.1.1";
	public static final String mail_receipt_was_read_before = "1.1.2";  
	public static final String mail_syntax_error = "1.2";
	public static final String mail_syntax_header_error = "1.2.1";  
	public static final String mail_syntax_header_contentid_error = "1.2.1.1";
	public static final String mail_syntax_header_contentid_missing = "1.2.1.1.1";  
	public static final String mail_syntax_header_dispo_to_error = "1.2.1.2";
	public static final String mail_syntax_header_dispo_to_missing = "1.2.1.2.1";  
	public static final String mail_syntax_header_contenttype = "1.2.1.3";
	public static final String mail_syntax_header_contenttype_missing = "1.2.1.3.1";   
	public static final String mail_syntax_body_error = "1.2.2";
	public static final String mail_syntax_body_empty = "1.2.2.1";  
	public static final String mail_syntax_body_missing = "1.2.2.2";  
	public static final String mail_attachment_error = "1.3";
	public static final String mail_attachment_corrupt = "1.3.1";  
	public static final String mail_mimetype_error = "1.4";
	public static final String mail_mimetype_not_processed = "1.4.1";  
	public static final String mail_mimetype_not_supported = "1.4.2";
	public static final String mail_security_error = "1.5";
	public static final String mail_security_signature_error = "1.5.1";  
	public static final String mail_security_signature_missing = "1.5.1.1";  
	public static final String mail_security_encryption_error = "1.5.2";
	public static final String mail_security_encryption_missing = "1.5.2.1";  
	public static final String mail_message_partial_error = "1.6";
	public static final String mail_message_partial_part_error = "1.6.1";  
	public static final String mail_message_partial_part_missing = "1.6.1.1";  
	public static final String mail_message_partial_part_twice = "1.6.1.2";
	public static final String mail_message_partial_part_header_error = "1.6.1.3";  
	public static final String mail_message_partial_part_header_id_error = "1.6.1.3.1";  
	public static final String mail_message_partial_part_header_id_missing = "1.6.1.3.1.1";  
	public static final String mail_message_partial_part_header_number_error = "1.6.1.3.2";
	public static final String mail_message_partial_part_header_number_missin = "1.6.1.3.2.1";  
	public static final String mail_message_partial_part_header_total_error = "1.6.1.3.3";
	public static final String mail_message_partial_part_header_total_missing = "1.6.1.3.3.1";  

}
