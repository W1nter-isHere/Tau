# v2.0.0 Neoforge-1.20.6 Major Update - Changes since Tau 1.0.3-1.19
## API Changes
- `Vector2i` renamed to `SimpleVec2i`
- Theme signature changes to work with the new Minecraft `GuiGraphics` API
- Removed `ScaleTransform` and `TranslationTransform`
- `Transformation` is no longer an interface but a class
- Added `scale` and `translate` methods to the Transform class to replace `ScaleTransform` and `TranslationTransform`
- `Transform` component will now also transform inputs given to the children components
- `PrimitiveComponent#build` will now take in a `BuildContext` instead of lists
- Added `ClientTooltipPositioner` parameter to `Tooltip`
- Added `menu` package
  - Adds container/menu support to Tau
  - Tau can now be used to create UI with inventories
  - Added new `PlayerInventory` and `ItemSlot` Component (These components will only function in a `TauMenu`)

## Internal Changes
- `ListView` no longer scrolls per child component and instead scrolls by a fixed amount
- `DynamicUIComponent` no longer exposes dangerous fields

# v2.0.1 Neoforge-1.20.6 Minor Update
## API Changes
- Renamed `TauMenu` to `UIMenu`
- Added `TauContainerMenu containerMenu` param to `UIMenu#build`
- Added `TauMenuHolder#openMenu`
- Removed `TauMenuHolder#newInstance`
- Added `UIMenu#tick`, `UIMenu#addDataSlots` and `UIMenu#stillValid`

# v2.0.2 Neoforge-1.20.6 Minor Update
## API Changes
- Renamed `TauContainerUI` to `TauContainerScreen`
- Added `UIMenu#createScreen` to add the ability to create child classes of TauContainerUI if need be

# v2.0.3 Neoforge-1.20.6 Minor Update
## Internal Changes
- Fixed container slots and container related things not being rendered

# v2.0.4 Neoforge-1.20.6 Minor Update
## API Changes
- Added `Variable` utility class that helps deal with values that change without having to rebuild the component tree
- `AnimatedTexture` Component now uses the new `Variable` System for the `uvPos` and `uvSize` fields
- Added `TauContainerMenu#getBlockEntity` helper methods

## Internal Changes
- Removed label renders in `TauContainerScreen`