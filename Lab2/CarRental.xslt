<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/carrental">
		<html>
			<head><title>RENTALS List</title></head>
			<body>
				<xsl:apply-templates select="rental"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="rental">
		<table>
			<h1>Make = <xsl:value-of select="make"/></h1><br/>
			<h2>Model = <xsl:value-of select="model"/></h2><br/>
			<h3>Number of days = <xsl:value-of select="nofdays"/></h3><br/>
			<h3>Number of units = <xsl:value-of select="nofunits"/></h3><br/>
			<h4>Discount = <xsl:value-of select="discount"/></h4><br/>
		</table>
	</xsl:template>
</xsl:stylesheet>