package com.iveiv.stored;

import org.springframework.data.jpa.repository.query.Procedure;


/**
 * criteria
 * @author irays
 *
 */
public interface UserRepo {
	
	@Procedure("plus1inout")
	Integer explicitlyNamedPlus1inout(Integer arg);

}
