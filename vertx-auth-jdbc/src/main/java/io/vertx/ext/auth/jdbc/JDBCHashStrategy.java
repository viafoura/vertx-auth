/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.auth.jdbc;

import io.vertx.core.json.JsonArray;

import java.util.List;

/**
 * Determines how the hashing is computed in the implementation
 *
 * You can implement this to provide a different hashing strategy to the default.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public interface JDBCHashStrategy {

  /**
   * Compute a random salt.
   *
   * @return a non null salt value
   */
  String generateSalt();

  /**
   * Compute the hashed password given the unhashed password and the salt
   * @param password  the unhashed password
   * @param salt  the salt
   * @param version the nonce version to use
   * @return  the hashed password
   */
  String computeHash(String password, String salt, int version);

  /**
   * Retrieve the hashed password from the result of the authentication query
   * @param row  the row
   * @return  the hashed password
   */
  String getHashedStoredPwd(JsonArray row);

  /**
   * Retrieve the salt from the result of the authentication query
   * @param row  the row
   * @return  the salt
   */
  String getSalt(JsonArray row);

  /**
   * Sets a ordered list of nonces where each position corresponds to a version.
   *
   * The nonces are supposed not to be stored in the underlying jdbc storage but to
   * be provided as a application configuration. The idea is to add one extra variable
   * to the hash function in order to make breaking the passwords using rainbow tables
   * or precomputed hashes harder. Leaving the attacker only with the brute force
   * approach.
   *
   * @param nonces a List of non null Strings.
   */
  void setNonces(List<String> nonces);
}
