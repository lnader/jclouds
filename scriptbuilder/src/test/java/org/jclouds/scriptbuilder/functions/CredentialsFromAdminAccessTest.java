/**
 *
 * Copyright (C) 2011 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.scriptbuilder.functions;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.testng.Assert.assertEquals;

import org.jclouds.domain.Credentials;
import org.jclouds.scriptbuilder.domain.Statement;
import org.jclouds.scriptbuilder.domain.Statements;
import org.jclouds.scriptbuilder.statements.login.AdminAccess;
import org.testng.annotations.Test;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class CredentialsFromAdminAccessTest {

   public void testWhenNotAdminAccess() {

      Statement statement = Statements.exec("echo hello");
      assertEquals(CredentialsFromAdminAccess.INSTANCE.apply(statement), null);

      Statement statementList = Statements.newStatementList(statement);
      assertEquals(CredentialsFromAdminAccess.INSTANCE.apply(statementList), null);

   }

   public void testWhenAdminAccess() {
      AdminAccess.Configuration configuration = createMock(AdminAccess.Configuration.class);
      AdminAccess statement = createMock(AdminAccess.class);
      Credentials creds = createMock(Credentials.class);

      expect(statement.getAdminCredentials()).andReturn(creds);

      replay(configuration);
      replay(statement);
      replay(creds);

      assertEquals(CredentialsFromAdminAccess.INSTANCE.apply(statement), creds);

      verify(configuration);
      verify(statement);
      verify(creds);
   }

   public void testWhenAdminAccessInsideList() {
      AdminAccess.Configuration configuration = createMock(AdminAccess.Configuration.class);
      AdminAccess statement = createMock(AdminAccess.class);
      Credentials creds = createMock(Credentials.class);

      expect(statement.getAdminCredentials()).andReturn(creds);

      replay(configuration);
      replay(statement);
      replay(creds);

      assertEquals(CredentialsFromAdminAccess.INSTANCE.apply(Statements.newStatementList(statement)), creds);

      verify(configuration);
      verify(statement);
      verify(creds);
   }
}
