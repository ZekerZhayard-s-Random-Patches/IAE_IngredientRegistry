package io.github.zekerzhayard.iae_ingredientregistry.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("mezz.jei.ingredients.IngredientRegistry".equals(transformedName)) {
            ClassNode cn = new ClassNode();
            new ClassReader(basicClass).accept(cn, ClassReader.EXPAND_FRAMES);
            for (MethodNode mn : cn.methods) {
                if (RemapUtils.checkMethodName(cn.name, mn.name, mn.desc, "<init>") && RemapUtils.checkMethodDesc(mn.desc, "(Lmezz/jei/startup/IModIdHelper;Lmezz/jei/ingredients/IngredientBlacklistInternal;Ljava/util/Map;Lcom/google/common/collect/ImmutableMap;Lcom/google/common/collect/ImmutableMap;)V")) {
                    LabelNode ln0 = new LabelNode(), ln1 = new LabelNode();
                    for (AbstractInsnNode ain : mn.instructions.toArray()) {
                        if (ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                            MethodInsnNode min = (MethodInsnNode) ain;
                            if (RemapUtils.checkClassName(min.owner, "com/google/common/collect/ImmutableMap$Builder") && RemapUtils.checkMethodName(min.owner, min.name, min.desc, "put") && RemapUtils.checkMethodDesc(min.desc, "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;")) {
                                mn.instructions.set(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/iae_ingredientregistry/Hook", "checkAndPut", "(Lcom/google/common/collect/ImmutableMap$Builder;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;", false));
                            }
                        }
                    }
                }
            }
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            basicClass = cw.toByteArray();
        }
        return basicClass;
    }
}
