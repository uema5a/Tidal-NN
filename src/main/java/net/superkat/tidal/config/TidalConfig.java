package net.superkat.tidal.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class TidalConfig extends MidnightConfig {
    // A proper config screen will come soon
    // MidnightLib will be used for BlanketCon versions(bc its fast & easy), YetAnotherConfigLib(YACL) will be used afterward
    // For now though, this exists to make it easy to implement configurable stuff whilst making the code

    public static final String WAVES = "waves";
    public static final String WET = "wet";

    @Comment(category = WAVES, centered = true) public static Comment reloadReminder;
    @Entry(category = WAVES, isSlider = true, min = 3, max = 16) public static int chunkRadius = 5;
    @Entry(category = WAVES, min = 1, max = 1024) public static int chunkUpdatesRescanAmount = 50;
    @Entry(category = WAVES, min = -2048, max = 2048) public static int maxRenderY = 2048;

    // Wet overlay = the "wet sand" patches left where waves wash up.
    //
    // The overlay texture uses an ordered-dither (Bayer) alpha pattern, so the opacity sliders control how many texels
    // survive the alpha cutout - i.e. how transparent the wet sand looks. The texture's RGB is white, so wetRed/Green/Blue
    // directly set the wet tint. All channels are 0-255, divided by 255 at render time.
    //
    // Vanilla and shaders need DIFFERENT opacities to look the same: vanilla additionally alpha-blends the surviving
    // dither texels (extra softening), while shaders draw them as opaque cutout. So a separate value is used for each,
    // picked automatically by Shader Mode (override with FORCE_ON / FORCE_OFF if detection is wrong).
    @Entry(category = WET) public static ShaderMode wetShaderMode = ShaderMode.AUTO;

    @Entry(category = WET, isSlider = true, min = 0, max = 255) public static int wetRed = 60;
    @Entry(category = WET, isSlider = true, min = 0, max = 255) public static int wetGreen = 52;
    @Entry(category = WET, isSlider = true, min = 0, max = 255) public static int wetBlue = 42;

    // Opacity WITHOUT shaders. Vanilla blends the dither on top of the cutout, so this needs to be high to be visible.
    @Entry(category = WET, isSlider = true, min = 0, max = 255) public static int wetAlpha = 150;
    // Opacity WITH shaders. Cutout keeps surviving texels opaque, so a low value already reads as a subtle wet sheen.
    @Entry(category = WET, isSlider = true, min = 0, max = 255) public static int wetAlphaShader = 45;

    // When true, the overlay is lit by the real sky/block light at its position (recommended, shader-friendly).
    // When false, it uses the legacy fully-dark lightmap (pack(0,0)).
    @Entry(category = WET) public static boolean wetUseWorldLight = true;

    public enum ShaderMode {
        AUTO,
        FORCE_ON,
        FORCE_OFF
    }

    @Entry(category = WAVES) public static boolean debug = false;
    @Comment(category = WAVES, centered = true) public static Comment debugDocs;
    @Comment(category = WAVES) public static Comment debugDocsSite;
    @Comment(category = WAVES) public static Comment debugDocsSpyglass;
    @Comment(category = WAVES) public static Comment debugDocsSpyglassHotbar;
    @Comment(category = WAVES) public static Comment debugDocsClock;
    @Comment(category = WAVES) public static Comment debugDocsCompass;

    public static int waveTicks = 80; // dummy value
    public static int waveDistFromShore = 8;

    public static boolean modEnabled = true; // unused for now because no time
}
