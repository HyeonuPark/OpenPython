package kr.pe.ecmaxp.openpie

import kr.pe.ecmaxp.thumbsj.signal.ControlPauseSignal
import kr.pe.ecmaxp.thumbsj.signal.ControlSignal
import kr.pe.ecmaxp.thumbsj.signal.ControlStopSignal
import li.cil.oc.api.machine.Architecture
import li.cil.oc.api.machine.ExecutionResult
import li.cil.oc.api.machine.Machine
import li.cil.oc.api.machine.Signal
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.attribute.FileTime

@Architecture.Name("OpenPie (micropython 3)")
class OpenPieArchitecture(private val machine: Machine) : Architecture {
    private var initialized: Boolean = false

    private var vm: OpenPieVirtualMachine? = null
    private var lastSynchronizedResult: ExecutionResult? = null

    override fun isInitialized(): Boolean {
        return initialized
    }

    override fun recomputeMemory(iterable: Iterable<ItemStack>): Boolean {
        // vm.getTotalMemorySize();
        // System.out.println(toString() + ": recomputeMemory()");
        return true
    }

    // TODO: report exception handler?

    override fun initialize(): Boolean {
        close()

        try {
            vm = OpenPieVirtualMachine(machine)
            initialized = vm!!.init()
            return initialized
        } catch (e: Exception) {
            e.printStackTrace()
            initialized = false
            return initialized
        }

    }

    override fun close() {
        if (vm != null) {
            vm!!.close()
            vm = null
        }
    }

    @Synchronized
    override fun runSynchronized() {
        try {
            this.lastSynchronizedResult = vm!!.step(true)
        } catch (e: Exception) {
            e.printStackTrace()
            this.lastSynchronizedResult = ExecutionResult.Error(e.toString())
        }

    }

    @Synchronized
    override fun runThreaded(isSynchronizedReturn: Boolean): ExecutionResult? {
        val prev = DebugFirmwareGetLastModifiedTime()
        val result: ExecutionResult?

        if (!isSynchronizedReturn) {
            // calling
            try {
                result = vm!!.step(false)
            } catch (e: Exception) {
                e.printStackTrace()
                return ExecutionResult.Error(e.toString())
            }

            val next = DebugFirmwareGetLastModifiedTime()
            return if (prev != null && prev != next) ExecutionResult.Shutdown(true) else result

        } else {
            result = this.lastSynchronizedResult
            this.lastSynchronizedResult = null
            return result
        }
    }

    private fun DebugFirmwareGetLastModifiedTime(): FileTime? {
        val file = File("C:\\Users\\EcmaXp\\Dropbox\\Projects\\openpie\\oprom\\build\\firmware.bin")
        try {
            return Files.getLastModifiedTime(file.toPath())
        } catch (ignored: IOException) {
        }

        return null
    }

    override fun onSignal() {
        val signal = machine.popSignal()
        vm!!.onSignal(signal)
    }

    override fun onConnect() {
        println(toString() + ": onConnect()")
    }

    override fun load(nbtTagCompound: NBTTagCompound) {
        // System.out.println(toString() + ": loadNBT()");
    }

    override fun save(nbtTagCompound: NBTTagCompound) {
        // System.out.println(toString() + ": saveNBT()");
    }

    override fun toString(): String {
        return "OpenPieArchitecture{" +
                "vm=" + vm +
                '}'.toString()
    }
}
