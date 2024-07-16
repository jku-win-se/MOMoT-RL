/// *******************************************************************************
// * Copyright (c) 2015 Vienna University of Technology.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *
// * Contributors:
// * Martin Fleck (Vienna University of Technology) - initial API and implementation
// *
// * Initially developed in the context of ARTIST EU project www.artist-project.eu
// *******************************************************************************/
// package at.ac.tuwien.big.momot.search.engine.script.ocl;
//
// import at.ac.tuwien.big.moea.util.CastUtil;
//
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.Reader;
//
// import javax.script.AbstractScriptEngine;
// import javax.script.Bindings;
// import javax.script.ScriptContext;
// import javax.script.ScriptEngineFactory;
// import javax.script.ScriptException;
// import javax.script.SimpleBindings;
//
// import org.eclipse.emf.ecore.EObject;
// import org.eclipse.jdt.annotation.NonNull;
// import org.eclipse.ocl.pivot.ExpressionInOCL;
// import org.eclipse.ocl.pivot.utilities.OCL;
// import org.eclipse.ocl.pivot.utilities.OCLHelper;
// import org.eclipse.ocl.pivot.utilities.ParserException;
//
// public class old_OCLScriptEngine extends AbstractScriptEngine {
//
// public static final String CONTEXT_VARIABLE = "self";
// public static final String PROTOCOL_PREFIX = "ocl:";
//
// protected static final String ATTRIBUTE_DEFINITION = "%s: %s = %s";
// protected static final String ATTRIBUTE_DEFINITION_STRING = "%s: %s = '%s'";
//
// protected static final String LET_EXPRESSION = "let " + ATTRIBUTE_DEFINITION + " in";
// protected static final String LET_EXPRESSION_STRING = "let " + ATTRIBUTE_DEFINITION_STRING + " in";
//
// protected static Boolean toBoolean(final Object obj) {
// if(obj instanceof Boolean) {
// return (Boolean) obj;
// }
// return false;
// }
//
// protected ScriptEngineFactory factory;
//
// public old_OCLScriptEngine() {
// }
//
// public old_OCLScriptEngine(final OCLScriptEngineFactory factory) {
// this.factory = factory;
// }
//
// @Override
// public Bindings createBindings() {
// return new SimpleBindings();
// }
//
// @Override
// public Object eval(final Reader reader, final ScriptContext context) throws ScriptException {
// final BufferedReader in = new BufferedReader(reader);
// String line = null;
// final StringBuilder rslt = new StringBuilder();
// try {
// while((line = in.readLine()) != null) {
// rslt.append(line);
// }
// } catch(final IOException e) {
// e.printStackTrace();
// }
// return eval(rslt.toString(), context);
// }
//
// @Override
// public Object eval(final String script, final ScriptContext context) throws ScriptException {
// final Boolean result = evalAsAttributeDefinitions(script, context);
// // unfortunately, let expressions are up to four times slower than
// // creating a new environment and (re-)define attributes.
// // evalWithLetExpressions(script, context);
// return result;
// }
//
// public Boolean evalAsAttributeDefinitions(final String script, final ScriptContext context) throws ScriptException {
// final EObject self = CastUtil.asClass(context.getAttribute(CONTEXT_VARIABLE), EObject.class);
// if(self == null) {
// return false;
// }
//
// // final Helper helper = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE).createOCLHelper();
//
// OCLHelper helper = null;
// try {
// /*
// * EcoreEnvironmentFactory.INSTANCE.createOCL
// * OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
// */
// helper = OCL.newInstance().createOCLHelper(self.eClass());
// } catch(final ParserException e1) {
// // TODO Auto-generated catch block
// e1.printStackTrace();
// }
// // helper.setContext(self.eClass());
//
// try {
// final Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
// // for(final Entry<String, Object> e : bindings.entrySet()) {
// // final String name = e.getKey();
// // if(name.equals(CONTEXT_VARIABLE) || e.getValue() == null) {
// // continue;
// // }
// //
// // final Object value = e.getValue();
// // final String typeName = value instanceof DynamicEObjectImpl
// // ? ((DynamicEObjectImpl) value).eClass().getName()
// // : value.getClass().getSimpleName();
// //
// // if(typeName.equals(String.class.getSimpleName())) {
// // // helper.defineAttribute(String.format(ATTRIBUTE_DEFINITION_STRING, name, typeName, value));
// // helper.createDerivedValueExpression(String.format(ATTRIBUTE_DEFINITION_STRING, name, typeName, value));
// // } else if(!"InterpretedFunction".equals(typeName)) {
// // // helper.defineAttribute(String.format(ATTRIBUTE_DEFINITION, name, typeName, value));
// // helper.createDerivedValueExpression(String.format(ATTRIBUTE_DEFINITION, name, typeName, value));
// // } else {
// // System.err.println("Context dirty :-(");
// // }
// // }
//
// final @NonNull ExpressionInOCL query = helper.createQuery(script.replace("blockid", "'b1'"));
//
// return toBoolean(helper.getOCL().createQuery(query).evaluateUnboxed(self));
// } catch(final ParserException e) {
// return false; // fail silently
// }
// }
//
// // public Boolean evalAsLetExpressions(final String script, final ScriptContext context) {
// // final EObject self = CastUtil.asClass(context.getAttribute(CONTEXT_VARIABLE), EObject.class);
// // if(self == null) {
// // return false;
// // }
// //
// // final OCLHelper helper = OCL.newInstance().createOCLHelper(self.eClass());
// // try {
// // final Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
// // String letExpressions = "";
// // for(final Entry<String, Object> e : bindings.entrySet()) {
// // final String name = e.getKey();
// // if(name.equals(CONTEXT_VARIABLE)) {
// // continue;
// // }
// // final Object value = e.getValue();
// // if(value == null) {
// // continue;
// // }
// //
// // String typeName = e.getValue().getClass().getSimpleName();
// // if(value instanceof DynamicEObjectImpl) {
// // typeName = ((DynamicEObjectImpl) value).eClass().getName();
// // }
// //
// // if(typeName.equals(String.class.getSimpleName())) {
// // letExpressions += String.format(LET_EXPRESSION_STRING, name, typeName, value);
// // } else {
// // letExpressions += String.format(LET_EXPRESSION, name, typeName, value);
// // }
// // }
// // final OCLExpression query = helper.createQuery(letExpressions + script);
// // final Object result = helper.getOCL().createQuery(query).evaluate(self);
// // return toBoolean(result);
// // } catch(final ParserException e) {
// // e.printStackTrace();
// // return false;
// // }
// // }
//
// @Override
// public ScriptEngineFactory getFactory() {
// return factory;
// }
//
// public void setFactory(final ScriptEngineFactory factory) {
// this.factory = factory;
// }
//
// }
