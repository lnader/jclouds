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
package org.jclouds.scriptbuilder.statements.login;

import org.jclouds.scriptbuilder.domain.OsFamily;
import org.testng.annotations.Test;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit")
public class ReplaceShadowPasswordEntryOfLoginUserTest {

   public void testWithPasswordUNIX() {
      String cmd = new ReplaceShadowPasswordEntryOfLoginUser("bar").render(OsFamily.UNIX);
      assert cmd.startsWith("awk -v user=^${SUDO_USER:=${USER}}: -v password='$6$") : cmd;
      assert cmd
            .endsWith("' 'BEGIN { FS=OFS=\":\" } $0 ~ user { $2 = password } 1' /etc/shadow >/etc/shadow.${SUDO_USER:=${USER}}\ntest -f /etc/shadow.${SUDO_USER:=${USER}} && mv /etc/shadow.${SUDO_USER:=${USER}} /etc/shadow\n") : cmd;
   }

   @Test(expectedExceptions = UnsupportedOperationException.class)
   public void testAddUserWindowsNotSupported() {
      new ReplaceShadowPasswordEntryOfLoginUser("password").render(OsFamily.WINDOWS);
   }
}
