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
package org.jclouds.http;

/**
 * // TODO: Adrian: Document this!
 * 
 * NOTE: {@link #filter} must be idempotent in a sense that multiple calls to it with the same
 * request yield in the same output. Example: this is required for request retrial (
 * {@link org.jclouds.http.internal.BaseHttpCommandExecutorService}, so that signatures can be
 * updated.
 * 
 * @author Adrian Cole
 */
public interface HttpRequestFilter {
   // note this is not generic typed, as http implementations do not care
   // about subclasses.
   HttpRequest filter(HttpRequest request) throws HttpException;
}
