/**
 */
package momot.examples.refactoring.refactoring;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link momot.examples.refactoring.refactoring.NamedElement#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see momot.examples.refactoring.refactoring.RefactoringPackage#getNamedElement()
 * @model
 * @generated
 */
public interface NamedElement extends EObject {
   /**
    * Returns the value of the '<em><b>Name</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Name</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
    * 
    * @return the value of the '<em>Name</em>' attribute.
    * @see #setName(String)
    * @see momot.examples.refactoring.refactoring.RefactoringPackage#getNamedElement_Name()
    * @model
    * @generated
    */
   String getName();

   /**
    * Sets the value of the '{@link momot.examples.refactoring.refactoring.NamedElement#getName
    * <em>Name</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * 
    * @param value
    *           the new value of the '<em>Name</em>' attribute.
    * @see #getName()
    * @generated
    */
   void setName(String value);

} // NamedElement
