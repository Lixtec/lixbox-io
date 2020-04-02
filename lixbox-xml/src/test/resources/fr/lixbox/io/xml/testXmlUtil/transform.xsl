<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>

<xsl:template match="imdgXml">
   <imdgXml> 
	 <xsl:for-each select="produit">
	   <produit>
 		<xsl:apply-templates select="classe" />
 		<etatmatiere>SOL</etatmatiere>
 		<xsl:apply-templates select="documentations" />
		<xsl:apply-templates select="groupeEmballage" />
 		<xsl:apply-templates select="interditClasse1" />
 		<xsl:apply-templates select="interditClasse1" />
 		<xsl:apply-templates select="tempCritRequise" />
 		<xsl:apply-templates select="tempRegulRequise" />
 		<xsl:apply-templates select="designationOffTransport" />
 		<xsl:apply-templates select="marchandiseInterdite" />
 		<xsl:apply-templates select="quantiteLimitee" />
 		<xsl:apply-templates select="numeroOnu" />
 		<xsl:apply-templates select="polluantMarin" />
 		<xsl:apply-templates select="risqueSubsidiaire1" />
 		<xsl:apply-templates select="risqueSubsidiaire2" />
 		<separede>
 		  <xsl:apply-templates select="arrimageSeparation" />
 		</separede>
 		<xsl:apply-templates select="tempCrit" />
 		<xsl:apply-templates select="tempFlashPointMax" />
 		<xsl:apply-templates select="tempFlashPointMin" />
 		<xsl:apply-templates select="tempRegul" />
 		<xsl:apply-templates select="variant" />
	   </produit>
      </xsl:for-each>
      
      
      <xsl:for-each select="refProduit">
	    <refProduit> 
	 		<xsl:apply-templates select="classe" />
	 		<xsl:apply-templates select="groupeEmballage" />
	 		<xsl:apply-templates select="numeroOnu" />
	 		<etatmatiere>SOL</etatmatiere>
	 		<xsl:apply-templates select="variant" />
	    </refProduit> 
	  </xsl:for-each>
	
	<xsl:for-each select="resultTrait">
	 <resultTrait>
 		<xsl:apply-templates />
 	 </resultTrait>
	</xsl:for-each>
	 
  </imdgXml> 
</xsl:template>



<xsl:template match="resultTrait">
    <xsl:element name="resultTrait" >
       <xsl:copy-of select="*" />
   </xsl:element>
</xsl:template>

<xsl:template match="*|@*">
  <xsl:copy> 
    <xsl:apply-templates select="@*"/>   
    <xsl:apply-templates select="node()"/>  
   </xsl:copy> 
 	
</xsl:template>

<xsl:template match="classe">
    <xsl:element name="classe" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="documentations">
  <xsl:if test="type= 'F_DEV' ">
   <xsl:element name="fichedeversement" >
       <xsl:value-of select="code" />
   </xsl:element>
 </xsl:if>  
 <xsl:if test="type= 'F_INC' ">
   <xsl:element name="ficheincendie" >
       <xsl:value-of select="code" />
   </xsl:element>
 </xsl:if>  
</xsl:template>


<xsl:template match="numeroOnu">
    <xsl:element name="numeroOnu" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="interditClasse1">
    <xsl:element name="interditClasse1" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="groupeEmballage">
    <xsl:element name="groupeEmballage" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="tempCritRequise">
    <xsl:element name="istempcritrequise" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="tempRegulRequise">
    <xsl:element name="istempregulrequise" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="designationOffTransport">
    <xsl:element name="libellefrancais" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="marchandiseInterdite">
    <xsl:element name="marchandiseinterdite" >
      <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="quantiteLimitee">
    <xsl:element name="massequantitelimitee" >
       <xsl:copy-of select="*" />
   </xsl:element>
</xsl:template>

<xsl:template match="polluantMarin">
    <xsl:element name="polluantmarin" >
       <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="risqueSubsidiaire1">
    <xsl:element name="risquesubsidiaire1" >
       <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

<xsl:template match="risqueSubsidiaire2">
    <xsl:element name="risquesubsidiaire2" >
       <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>


<xsl:template match="arrimageSeparation">
       <xsl:value-of select="." /> 
</xsl:template>


<xsl:template match="tempCrit">
    <xsl:element name="tempcrit" >
       <xsl:copy-of select="*" />
   </xsl:element>
</xsl:template>

<xsl:template match="tempFlashPointMax">
    <xsl:element name="tempFlashPointMax" >
       <xsl:copy-of select="*" />
   </xsl:element>
</xsl:template>

<xsl:template match="tempFlashPointMin">
    <xsl:element name="tempFlashPointMin" >
       <xsl:copy-of select="*" />
   </xsl:element>
</xsl:template>

<xsl:template match="tempRegul">
    <xsl:element name="tempRegul" >
       <xsl:copy-of select="*" />
   </xsl:element>
</xsl:template>

<xsl:template match="variant">
    <xsl:element name="variant" >
       <xsl:value-of select="."/>
   </xsl:element>
</xsl:template>

</xsl:stylesheet>