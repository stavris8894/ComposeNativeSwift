package com.composenative.swift

import com.composenative.swift.components.*
import com.composenative.swift.core.*
import kotlin.test.*

class ComposeNativeCoreTest {

    @Test
    fun testModifierChaining() {
        val modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(CNColor.Primary)
            .cornerRadius(8.dp)

        val elements = mutableListOf<CNModifier.Element>()
        modifier.foldIn(Unit) { _, el -> elements.add(el) }

        assertEquals(4, elements.size)
        assertTrue(elements.any { it is CNFillMaxWidthModifier })
        assertTrue(elements.any { it is CNPaddingModifier })
        assertTrue(elements.any { it is CNBackgroundModifier })
        assertTrue(elements.any { it is CNCornerRadiusModifier })
    }

    @Test
    fun testColorParsing() {
        val hexColor = CNColor("#007AFF")
        assertEquals(0, hexColor.red)
        assertEquals(122, hexColor.green)
        assertEquals(255, hexColor.blue)
        assertEquals(1.0f, hexColor.alpha)

        val hexAlpha = CNColor("#80FF0000")
        assertEquals(255, hexAlpha.red)
        assertEquals(0, hexAlpha.green)
        assertEquals(0, hexAlpha.blue)
        assertTrue(hexAlpha.alpha in 0.49f..0.51f)
    }

    @Test
    fun testTreeHierarchyGeneration() {
        var clicked = false
        val column = Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            add(Text("Hello Compose Native!"))
            add(
                Button(onClick = { clicked = true }) {
                    Text("Click Me")
                }
            )
            add(
                Row(modifier = Modifier.fillMaxWidth()) {
                    add(Text("Item 1"))
                    add(Spacer(modifier = Modifier.width(8.dp)))
                    add(Text("Item 2"))
                }
            )
        }

        assertEquals(3, column.children.size)
        assertTrue(column.children[0] is CNTextNode)
        assertTrue(column.children[1] is CNButtonNode)
        assertTrue(column.children[2] is CNRowNode)

        val btn = column.children[1] as CNButtonNode
        btn.onClick()
        assertTrue(clicked)

        val row = column.children[2] as CNRowNode
        assertEquals(3, row.children.size)
    }

    @Test
    fun testReactiveScreenState() {
        var renderCount = 0

        class TestScreen : CNScreen() {
            var counter by mutableStateOf(0)

            override fun build(): CNNode = Column {
                add(Text("Count: $counter"))
                add(Button(onClick = { counter++ }) {
                    Text("Increment")
                })
            }
        }

        val screen = TestScreen()
        screen.addListener { renderCount++ }

        val root1 = screen.render() as CNColumnNode
        val text1 = root1.children[0] as CNTextNode
        assertEquals("Count: 0", text1.text)

        val btn = root1.children[1] as CNButtonNode
        btn.onClick()

        assertEquals(1, renderCount)
        val root2 = screen.render() as CNColumnNode
        val text2 = root2.children[0] as CNTextNode
        assertEquals("Count: 1", text2.text)
    }
}
