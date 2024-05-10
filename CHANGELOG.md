# v2.0.0 Neoforge-1.20.4 Major Update - Changes since Tau 1.0.3-1.19
## API Changes
- `Vector2i` renamed to `SimpleVec2i`
- Theme signature changes to work with the new Minecraft `GuiGraphics` API
- Removed `ScaleTransform` and `TranslationTransform`
- `Transformation` is no longer an interface but a class
- Added `scale` and `translate` methods to the Transform class to replace `ScaleTransform` and `TranslationTransform`
- `Transform` component will now also transform inputs given to the children components

## Internal Changes
- `ListView` no longer scrolls per child component and instead scrolls by a fixed amount
