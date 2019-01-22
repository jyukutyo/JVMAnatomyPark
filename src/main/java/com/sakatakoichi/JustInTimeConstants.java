package com.sakatakoichi;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.LinuxPerfNormProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class JustInTimeConstants {

    static final long x_static_final = Long.getLong("divisor", 1000);
    static       long x_static       = Long.getLong("divisor", 1000);
    final long x_inst_final   = Long.getLong("divisor", 1000);
    long x_inst         = Long.getLong("divisor", 1000);

    @Benchmark
    public long _static_final() { return 1000 / x_static_final; }
    @Benchmark public long _static()       { return 1000 / x_static;       }
    @Benchmark public long _inst_final()   { return 1000 / x_inst_final;   }
    @Benchmark public long _inst()         { return 1000 / x_inst;         }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JustInTimeConstants.class.getSimpleName())
//                .addProfiler(LinuxPerfProfiler.class)
                .addProfiler(LinuxPerfNormProfiler.class)
//                .addProfiler(LinuxPerfAsmProfiler.class)
//                .addProfiler(WinPerfAsmProfiler.class)
//                .addProfiler(DTraceAsmProfiler.class)
                .build();

        new Runner(opt).run();
    }

}