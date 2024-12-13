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

# v2.1.0 Neoforge-1.20.6 Major Update
## Bug Fixes
- UIMenus no longer crash when used in a server

## API Changes
- Added `Variable` utility class that helps deal with values that change without having to rebuild the component tree
- `AnimatedTexture` Component now uses the new `Variable` System for the `uvPos` and `uvSize` fields
- Added `TauContainerMenu#getBlockEntity` helper methods
- Removed `TauMenuHolder#registerScreen`
- Added `TauMenuHelper#registerMenuScreen`
- Moved sound playing methods from `UIComponent` to `ClientSoundHelper`
- Added `UIMenu#getSlots` that you must override if you want to have item slots in the UI
- Added `addDataSlot` overload in `TauContainerMenu` to take in a getter and setter instead of `DataSlot`
- Added `createMenu` method to `UIMenu` to add the ability to create child classes of `TauContainerMenu`
- Added `onClose` method to `UIMenu`
- `UIMenu` is now an abstract class instead of an interface
- `UIMenu` can now be rebuilt by calling `rebuild()` - Note, rebuilding does not disable/enable item slots, check example to see how to create dynamic slots
- Added `drawSlider` to `Theme`

## Internal Changes
- Removed label renders in `TauContainerScreen`
- When creating the container on the server side, it no longer builds the UI, instead calls the getSlots function to create the slots 