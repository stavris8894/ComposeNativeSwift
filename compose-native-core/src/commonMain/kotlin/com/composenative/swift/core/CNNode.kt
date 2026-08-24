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

class CNFlowRowNode(
    id: String = generateNodeId("flow_row"),
    modifier: CNModifier = CNModifier.None,
    val horizontalArrangement: CNArrangement = CNArrangement.Start,
    val verticalArrangement: CNArrangement = CNArrangement.Top,
    val maxItemsInEachRow: Int = Int.MAX_VALUE,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

class CNFlowColumnNode(
    id: String = generateNodeId("flow_col"),
    modifier: CNModifier = CNModifier.None,
    val horizontalArrangement: CNArrangement = CNArrangement.Start,
    val verticalArrangement: CNArrangement = CNArrangement.Top,
    val maxItemsInEachColumn: Int = Int.MAX_VALUE,
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

class CNVerticalDividerNode(
    id: String = generateNodeId("v_divider"),
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
// Button & Action Nodes
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

class CNExtendedFabNode(
    id: String = generateNodeId("ext_fab"),
    modifier: CNModifier = CNModifier.None,
    val onClick: () -> Unit,
    val text: String,
    val icon: CNIconNode? = null,
    val expanded: Boolean = true,
    val containerColor: CNColor = CNColor.Primary,
    val contentColor: CNColor = CNColor.OnPrimary
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Chips & Segmented Controls
// -------------------------------------------------------------------------

enum class CNChipType {
    Filter,
    Assist,
    Input,
    Suggestion
}

class CNChipNode(
    id: String = generateNodeId("chip"),
    modifier: CNModifier = CNModifier.None,
    val text: String,
    val selected: Boolean = false,
    val onClick: () -> Unit,
    val leadingIcon: CNIconNode? = null,
    val trailingIcon: CNIconNode? = null,
    val chipType: CNChipType = CNChipType.Filter,
    val enabled: Boolean = true
) : CNNode(id, modifier)

data class CNSegmentItem(
    val id: String,
    val label: String,
    val icon: String? = null
)

class CNSegmentedButtonNode(
    id: String = generateNodeId("seg_btn"),
    modifier: CNModifier = CNModifier.None,
    val items: List<CNSegmentItem>,
    val selectedIndex: Int,
    val onSelectIndex: (Int) -> Unit,
    val enabled: Boolean = true
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Input & Selection Nodes
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

class CNRangeSliderNode(
    id: String = generateNodeId("range_slider"),
    modifier: CNModifier = CNModifier.None,
    val startValue: Float,
    val endValue: Float,
    val onValuesChange: (start: Float, end: Float) -> Unit,
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

class CNRadioButtonNode(
    id: String = generateNodeId("rb"),
    modifier: CNModifier = CNModifier.None,
    val selected: Boolean,
    val onClick: () -> Unit,
    val label: String? = null,
    val enabled: Boolean = true,
    val selectedColor: CNColor = CNColor.Primary
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// List, Grid & Item Nodes
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

sealed class CNGridCells {
    data class Fixed(val count: Int) : CNGridCells()
    data class Adaptive(val minSize: CNDp) : CNGridCells()
}

class CNLazyGridNode(
    id: String = generateNodeId("lazy_grid"),
    modifier: CNModifier = CNModifier.None,
    val columns: CNGridCells = CNGridCells.Fixed(2),
    val horizontalArrangement: CNArrangement = CNArrangement.spacedBy(8.dp),
    val verticalArrangement: CNArrangement = CNArrangement.spacedBy(8.dp),
    val contentPadding: CNPadding = CNPadding.Zero,
    val children: List<CNNode> = emptyList()
) : CNNode(id, modifier)

class CNListItemNode(
    id: String = generateNodeId("list_item"),
    modifier: CNModifier = CNModifier.None,
    val headlineContent: CNNode,
    val supportingContent: CNNode? = null,
    val leadingContent: CNNode? = null,
    val trailingContent: CNNode? = null,
    val overlineContent: CNNode? = null,
    val onClick: (() -> Unit)? = null
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Media & Container Nodes
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

class CNAccordionNode(
    id: String = generateNodeId("accordion"),
    modifier: CNModifier = CNModifier.None,
    val title: String,
    val isExpanded: Boolean,
    val onToggle: (Boolean) -> Unit,
    val leadingIcon: CNIconNode? = null,
    val content: CNNode
) : CNNode(id, modifier)

class CNBannerNode(
    id: String = generateNodeId("banner"),
    modifier: CNModifier = CNModifier.None,
    val text: String,
    val icon: CNIconNode? = null,
    val primaryActionText: String? = null,
    val onPrimaryAction: (() -> Unit)? = null,
    val secondaryActionText: String? = null,
    val onSecondaryAction: (() -> Unit)? = null,
    val backgroundColor: CNColor = CNColor.PrimaryContainer
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

data class CNTabItem(
    val title: String,
    val icon: String? = null,
    val badge: String? = null
)

class CNTabRowNode(
    id: String = generateNodeId("tab_row"),
    modifier: CNModifier = CNModifier.None,
    val tabs: List<CNTabItem>,
    val selectedTabIndex: Int,
    val onTabSelected: (Int) -> Unit,
    val containerColor: CNColor = CNColor.Surface,
    val contentColor: CNColor = CNColor.Primary
) : CNNode(id, modifier)

data class CNNavigationItem(
    val id: String,
    val label: String,
    val icon: String,
    val selectedIcon: String? = null,
    val badge: String? = null
)

class CNNavigationBarNode(
    id: String = generateNodeId("nav_bar"),
    modifier: CNModifier = CNModifier.None,
    val items: List<CNNavigationItem>,
    val selectedIndex: Int,
    val onItemSelected: (Int) -> Unit,
    val containerColor: CNColor = CNColor.Surface,
    val contentColor: CNColor = CNColor.Primary
) : CNNode(id, modifier)

// -------------------------------------------------------------------------
// Progress, Feedback & Dialog Nodes
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

class CNDialogNode(
    id: String = generateNodeId("dialog"),
    modifier: CNModifier = CNModifier.None,
    val isVisible: Boolean,
    val onDismissRequest: () -> Unit,
    val title: String,
    val text: String,
    val confirmButtonText: String = "OK",
    val onConfirm: () -> Unit,
    val dismissButtonText: String? = null,
    val onDismiss: (() -> Unit)? = null
) : CNNode(id, modifier)

class CNBottomSheetNode(
    id: String = generateNodeId("sheet"),
    modifier: CNModifier = CNModifier.None,
    val isVisible: Boolean,
    val onDismissRequest: () -> Unit,
    val content: CNNode
) : CNNode(id, modifier)

class CNEmptyNode(id: String = generateNodeId("empty")) : CNNode(id, CNModifier.None)
