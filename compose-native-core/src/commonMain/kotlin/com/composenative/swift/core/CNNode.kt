package com.composenative.swift.core

private var idCounter = 0L
fun generateNodeId(prefix: String = "cn_node"): String = "$prefix-${++idCounter}"

/**
 * Base class for all ComposeNative UI tree nodes.
 */
sealed class CNNode(
    val id: String = generateNodeId("node"),
    val modifier: CNModifier = CNModifier.None
) {
    /**
     * Extracts all modifier elements for fast translation by the Swift renderer.
     */
    val modifierElements: List<CNModifier.Element> by lazy {
        val list = mutableListOf<CNModifier.Element>()
        modifier.foldIn(Unit) { _, element ->
            list.add(element)
        }
        list
    }
}

// -------------------------------------------------------------------------
// Layout Nodes
// -------------------------------------------------------------------------

class CNColumnNode(
    id: String = generateNodeId("col"),
    modifier: CNModifier = CNModifier.None,
    val verticalArrangement: CNArrangement = CNArrangement.Top,
    val horizontalAlignment: CNAlignment.Horizontal = CNAlignment.Start,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

class CNRowNode(
    id: String = generateNodeId("row"),
    modifier: CNModifier = CNModifier.None,
    val horizontalArrangement: CNArrangement = CNArrangement.Start,
    val verticalAlignment: CNAlignment.Vertical = CNAlignment.Top,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

class CNBoxNode(
    id: String = generateNodeId("box"),
    modifier: CNModifier = CNModifier.None,
    val contentAlignment: CNAlignment = CNAlignment.TopStart,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

class CNSpacerNode(
    id: String = generateNodeId("spacer"),
    modifier: CNModifier = CNModifier.None
) : CNNode(id, modifier)

class CNDividerNode(
    id: String = generateNodeId("divider"),
    modifier: CNModifier = CNModifier.None,
    val color: CNColor = CNColor.Divider,
    val thickness: CNDp = 1.dp
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Text & Typography Nodes
// -------------------------------------------------------------------------

class CNTextNode(
    id: String = generateNodeId("text"),
    modifier: CNModifier = CNModifier.None,
    val text: String,
    val style: CNTextStyle = CNTextStyle.Default,
    val maxLines: Int? = null,
    val overflow: CNTextOverflow = CNTextOverflow.Clip
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Button Nodes
// -------------------------------------------------------------------------

class CNButtonNode(
    id: String = generateNodeId("btn"),
    modifier: CNModifier = CNModifier.None,
    val onClick: () -> Unit,
    val style: CNButtonStyle = CNButtonStyle.Filled,
    val enabled: Boolean = true,
    val content: CNNode
) : CNNode(id, modifier)

class CNIconButtonNode(
    id: String = generateNodeId("icon_btn"),
    modifier: CNModifier = CNModifier.None,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val icon: CNIconNode
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Input & Control Nodes
// -------------------------------------------------------------------------

class CNTextFieldNode(
    id: String = generateNodeId("tf"),
    modifier: CNModifier = CNModifier.None,
    val value: String,
    val onValueChange: (String) -> Unit,
    val placeholder: String = "",
    val label: String = "",
    val isSecure: Boolean = false,
    val keyboardType: CNKeyboardType = CNKeyboardType.Default,
    val enabled: Boolean = true,
    val isError: Boolean = false,
    val singleLine: Boolean = true,
    val leadingIcon: CNIconNode? = null,
    val trailingIcon: CNIconNode? = null
) : CNNode(id, modifier)

class CNSwitchNode(
    id: String = generateNodeId("switch"),
    modifier: CNModifier = CNModifier.None,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit,
    val enabled: Boolean = true,
    val tint: CNColor = CNColor.Primary
) : CNNode(id, modifier)

class CNSliderNode(
    id: String = generateNodeId("slider"),
    modifier: CNModifier = CNModifier.None,
    val value: Float,
    val onValueChange: (Float) -> Unit,
    val valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    val steps: Int = 0,
    val enabled: Boolean = true,
    val activeColor: CNColor = CNColor.Primary
) : CNNode(id, modifier)

class CNCheckboxNode(
    id: String = generateNodeId("cb"),
    modifier: CNModifier = CNModifier.None,
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit,
    val enabled: Boolean = true,
    val checkedColor: CNColor = CNColor.Primary
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// List Nodes
// -------------------------------------------------------------------------

class CNLazyColumnNode(
    id: String = generateNodeId("lazy_col"),
    modifier: CNModifier = CNModifier.None,
    val verticalArrangement: CNArrangement = CNArrangement.Top,
    val contentPadding: CNPadding = CNPadding.Zero,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

class CNLazyRowNode(
    id: String = generateNodeId("lazy_row"),
    modifier: CNModifier = CNModifier.None,
    val horizontalArrangement: CNArrangement = CNArrangement.Start,
    val contentPadding: CNPadding = CNPadding.Zero,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Media Nodes
// -------------------------------------------------------------------------

class CNImageNode(
    id: String = generateNodeId("img"),
    modifier: CNModifier = CNModifier.None,
    val source: CNImageSource,
    val contentDescription: String? = null,
    val contentScale: CNContentScale = CNContentScale.Fit,
    val tint: CNColor? = null
) : CNNode(id, modifier)

class CNIconNode(
    id: String = generateNodeId("icon"),
    modifier: CNModifier = CNModifier.None,
    val icon: String,
    val contentDescription: String? = null,
    val tint: CNColor = CNColor.Current,
    val size: CNDp = 24.dp
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Surface & Container Nodes
// -------------------------------------------------------------------------

class CNCardNode(
    id: String = generateNodeId("card"),
    modifier: CNModifier = CNModifier.None,
    val shape: CNShape = CNShape.RoundedCorner(12.dp),
    val elevation: CNDp = 2.dp,
    val border: CNBorder? = null,
    val backgroundColor: CNColor = CNColor.Surface,
    val content: CNNode
) : CNNode(id, modifier)

class CNSurfaceNode(
    id: String = generateNodeId("surface"),
    modifier: CNModifier = CNModifier.None,
    val shape: CNShape = CNShape.Rectangle,
    val color: CNColor = CNColor.Surface,
    val contentColor: CNColor = CNColor.OnSurface,
    val border: CNBorder? = null,
    val elevation: CNDp = 0.dp,
    val content: CNNode
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Navigation & Structure Nodes
// -------------------------------------------------------------------------

class CNScaffoldNode(
    id: String = generateNodeId("scaffold"),
    modifier: CNModifier = CNModifier.None,
    val topBar: CNTopAppBarNode? = null,
    val bottomBar: CNNode? = null,
    val floatingActionButton: CNNode? = null,
    val content: CNNode
) : CNNode(id, modifier)

class CNTopAppBarNode(
    id: String = generateNodeId("appbar"),
    modifier: CNModifier = CNModifier.None,
    val title: String,
    val navigationIcon: CNIconButtonNode? = null,
    val actions: List<CNNode> = emptyList(),
    val backgroundColor: CNColor = CNColor.Surface,
    val contentColor: CNColor = CNColor.OnSurface
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Progress & Feedback Nodes
// -------------------------------------------------------------------------

class CNCircularProgressIndicatorNode(
    id: String = generateNodeId("c_progress"),
    modifier: CNModifier = CNModifier.None,
    val progress: Float? = null,
    val color: CNColor = CNColor.Primary,
    val strokeWidth: CNDp = 4.dp
) : CNNode(id, modifier)

class CNLinearProgressIndicatorNode(
    id: String = generateNodeId("l_progress"),
    modifier: CNModifier = CNModifier.None,
    val progress: Float? = null,
    val color: CNColor = CNColor.Primary,
    val trackColor: CNColor = CNColor.SurfaceVariant
) : CNNode(id, modifier)

class CNBadgeNode(
    id: String = generateNodeId("badge"),
    modifier: CNModifier = CNModifier.None,
    val text: String? = null,
    val backgroundColor: CNColor = CNColor.Error,
    val contentColor: CNColor = CNColor.White,
    val content: CNNode? = null
) : CNNode(id, modifier)

class CNEmptyNode(id: String = generateNodeId("empty")) : CNNode(id, CNModifier.None)
