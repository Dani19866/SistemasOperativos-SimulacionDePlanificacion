/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package structures;

/**
 *
 * @author Daniel
 */
public enum MemorySizeKb {
    KB_EQUALS_TO_2_GB(2097152),
    KB_EQUALS_TO_4_GB(4194304),
    KB_EQUALS_TO_8_GB(8388608),
    KB_EQUALS_TO_16_GB(16777216),
    KB_EQUALS_TO_32_GB(33554432);

    private final int size;
    private MemorySizeKb(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }
}
