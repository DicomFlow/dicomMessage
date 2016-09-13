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
package br.ufpb.dicomflow.integrationAPI.mail;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

import br.ufpb.dicomflow.integrationAPI.message.xml.ServiceIF;

public interface ReceiverIF {
	
	public List<ServiceIF> receive(FilterIF filter);
	
	public List<byte[]> receiveAttachs(FilterIF filter);
	
	public List<MessageIF> receiveMessages(FilterIF filter);
	
	public List<ServiceIF> receiveCipher(FilterIF filter, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey);
	
	public List<byte[]> receiveCipherAttachs(FilterIF filter, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey);
	
	public List<MessageIF> receiveCipherMessages(FilterIF filter, X509Certificate signCert, X509Certificate encryptCert, PrivateKey privateKey);

}
