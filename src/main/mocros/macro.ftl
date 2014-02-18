

<#--Macro to create custom input
* inputLabel - just input label
* inputType - type of selected input
* textOnTheLeftSide - is true display input label at the left side
* Example:  <@customInput inputLabel = ["Enter first name", "Enter last name"]
*                         inputType = "text"
*                         textOnTheLeftSide="true"/>
-->
<#macro customInput inputLabel inputType = "text" textOnTheLeftSide = "true">
    <#list inputLabel as x>
        <p><#if textOnTheLeftSide == "true">${x}</#if>
            <input type=${inputType}>
        <#if textOnTheLeftSide != "true">${x}</#if></p>
    </#list>
</#macro>

<#--Macro to create custom select
* options - options in select
* Example:   <@customSelect options = ["winter", "asprint", "summer", "autmn"]/>
-->
<#macro customSelect options>
    <p><select size="x_size">
    <#list options as x>
        <option value=${x}>${x}</option>
    </#list>
    </select></p>
</#macro>