<?xml version="1.0" encoding="UTF-8"?>
<!--
The MIT License (MIT)

Copyright (c) 2016-2022 Objectionary.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" id="expand-qqs" version="2.0">
  <!--
  Replace 'QQ' to 'org.eolang' for:
  - @base attribute for all objects;
  - child elements' text of all metas
  -->
  <xsl:output encoding="UTF-8" method="xml"/>
  <xsl:template match="o[@base='QQ']">
    <xsl:copy>
      <xsl:attribute name="base">.eolang</xsl:attribute>
      <xsl:copy-of select="@*[name()!='base']"/>
      <xsl:element name="o">
        <xsl:attribute name="base">.org</xsl:attribute>
        <xsl:copy-of select="@*[name()!='base']"/>
        <xsl:element name="o">
          <xsl:attribute name="base">Q</xsl:attribute>
          <xsl:copy-of select="@*[name()!='base']"/>
        </xsl:element>
      </xsl:element>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="meta/*[text()[matches(., '^QQ\..*')]]">
    <xsl:copy>
      <xsl:value-of select="replace(./text(), '^QQ\.', 'Q.org.eolang.')"/>
    </xsl:copy>
  </xsl:template>
  <xsl:template match="node()|@*">
    <xsl:copy>
      <xsl:apply-templates select="node()|@*"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
