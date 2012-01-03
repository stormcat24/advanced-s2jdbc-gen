package me.stormcat.maven.plugin.s2jdbcgen;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal generate
 * 
 * @phase process-sources
 */
public class GenerateCodeMojo extends AbstractMojo {
    
    /**
     * @parameter
     * @required
     */
    private String genDir;

    /**
     * @parameter
     * @required
     */
    private String host;
    
    /**
     * @parameter
     * @required
     */
    private String schema;
    
    /**
     * @parameter
     * @required
     */
    private String user;
    
    /**
     * @parameter
     * @required
     */
    private String password;
    
    /**
     * @parameter
     * @required
     */
    private String rootPackage;
    
    /**
     * @parameter
     */
    private DelFlag delFlag;
    
    public void execute() throws MojoExecutionException {
        GenerateCodeExecutor executor = new GenerateCodeExecutor(genDir, rootPackage, host, schema, user, password);
        executor.setDelFlag(delFlag);
        executor.execute();
    }
}
