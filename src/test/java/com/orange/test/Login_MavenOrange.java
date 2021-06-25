package com.orange.test;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orange.base.BaseClass_MavenOrange;
import com.orange.utils.CommonUtils_MavenOrange;

public class Login_MavenOrange extends BaseClass_MavenOrange {

	
	//@Test
	public static void login_001() throws Exception {
		boolean welcomeText = CommonUtils_MavenOrange.validLogin();
		Assert.assertTrue(welcomeText, "The welcome text is displayed");
	}
	
	
	@Test
	public static void login_002() throws Exception {
		boolean errorMsg = CommonUtils_MavenOrange.inValidLogin();
		Assert.assertFalse(errorMsg, "The error text is displayed");
	}
	
	//@Parameters({"userName","password"})
	//@Test(groups = {"smoke", "login_003"} )
	public static void login_003(String userName, String password) throws Exception{
		boolean welcomeText = CommonUtils_MavenOrange.validLoginWithParameters(userName, password);
		Assert.assertTrue(welcomeText, "The welcome text is displayed");
	}
	
	//@Test(dataProvider = "dataFromExcel", dataProviderClass =com.orange.dataproviders.DataProvider.class )
	public static void login_004(String userName, String password) throws Exception{
		boolean errorMsg = CommonUtils_MavenOrange.inValidLoginWithData(userName, password);
		Assert.assertTrue(errorMsg, "The error text is displayed");
	}
	
	
}
