/*
 * MIT License
 *
 * Copyright (c) 2019 PiggyPiglet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.piggypiglet.framework.file.objects;

import java.io.File;

public final class FileWrapper {
    private final File file;
    private String fileContent;

    public FileWrapper(File file, String fileContent) {
        this.file = file;
        this.fileContent = fileContent;
    }

    /**
     * Get the stored File instance
     * @return Stored file instance
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the stored raw content of the file as a String
     * @return File Content
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * Used for updating via FileManager#update, I don't recommend messing with this manually.
     * @param fileContent updated file content to replace the current
     */
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}