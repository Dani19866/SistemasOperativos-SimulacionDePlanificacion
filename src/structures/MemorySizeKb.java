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
    KB_2(2097152),
    KB_4(4194304),
    KB_8(8388608),
    KB_16(16777216),
    KB_32(33554432);

    private final int size;
    private MemorySizeKb(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }
}
