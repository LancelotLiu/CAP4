/* 
 * ZipUtilTest.java
 * 
 * Copyright (c) 2009-2018 International Integrated System, Inc. 
 * All Rights Reserved.
 * 
 * Licensed Materials - Property of International Integrated System, Inc.
 * 
 * This software is confidential and proprietary information of 
 * International Integrated System, Inc. (&quot;Confidential Information&quot;).
 */
package com.iisigroup.colabase.zip.tool;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <pre>
 * test zipUtil
 * </pre>
 * 
 * @since 2018年3月29日
 * @author JohnsonHo
 * @version
 *          <ul>
 *          <li>2018年3月29日,JohnsonHo,new
 *          </ul>
 */
public class ZipUtilTest {

    /** zip test 資料路徑 */
    private final String zipFilePath = "D:\\COLA\\testFile\\testZip\\testData.txt";
    private final String zipFileOutputPath = "D:\\COLA\\testFile\\zipComplete\\testZip";
    private final String zipFileName = "TestZip.zip";
    private final String zipFilePassword = "abc123";
    /** unzip test 資料路徑 */
    private final String unzipFilePath = "D:\\COLA\\testFile\\zipComplete\\testZip\\TestZip.zip";
    private final String unzipOutputFolder = "D:\\COLA\\testFile\\zipComplete\\testUnzip";
    /** check folder 資料路徑 */
    private final String emptyFolderPath = "D:\\COLA\\testFile\\zipComplete\\testEmptyFolder";
    private final String emptyFolder2Path = "D:\\COLA\\testFile\\zipComplete\\testEmptyFolder\\empty";
    /** check folder 資料路徑 */
    private final String existsFolderPath = "D:\\COLA\\testFile\\zipComplete\\testExists";
    
    /**
     * class初始化之後調用，用來作測試的準備工作。
     * 
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * class結束之前調用，用來作測試的清理工作。
     * 
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // testZip()--clean file
        // File zipOutputFile = new File(zipFileOutputPath, zipFileName);
        // if (zipOutputFile.exists()) {
        // zipOutputFile.delete();
        // }
    }

    /**
     * 在測試method前調用，用來作測試的準備工作
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * 在測試method後調用，用來作測試的清理工作。
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     * 測試壓縮功能。
     * 
     */
    @Test
    public void testZip() {
        ArrayList<File> files = new ArrayList<File>();
        files.add(new File(zipFilePath));
        
        File zipOutputFile = new File(zipFileOutputPath, zipFileName);
        try {
            ZipUtil.zip(zipOutputFile, true, zipFilePassword, files);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception!");
        }
        if (!zipOutputFile.exists()) {
            fail("檔案不存在!");
        }
    }

    /**
     * 測試解壓縮功能。
     * 
     */
    @Test
    public void testUnzip() {
        File unzipFile = new File(unzipFilePath);
        File unzipOutputFile = new File(unzipOutputFolder);
        try {
            ZipUtil.unzip(unzipFile, zipFilePassword, unzipOutputFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception!");
        }
        if (!unzipOutputFile.exists()) {
            fail("檔案不存在!");
        }
    }

    /**
     * 測試是否為空資料夾。
     * 
     */
    @Test
    public void testIsEmptyFolder() {
        try {
            ZipUtil.isEmptyFolder(false, emptyFolderPath, emptyFolder2Path);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception!");
        }
    }
    
    /**
     * 測試檔案是否存在。
     * 
     */
    @Test
    public void testIsExistsFolder() {
        try {
            ZipUtil.isExistsFolder(new File(existsFolderPath), true);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception!");
        }
    }

}
