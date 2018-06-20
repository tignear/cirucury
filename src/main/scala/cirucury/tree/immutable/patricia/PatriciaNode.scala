package cirucury.tree.immutable.patricia

trait PatriciaNode extends PatriciaTree.Patricia[PatriciaNode]{
  override def factory = PatriciaTree
}
