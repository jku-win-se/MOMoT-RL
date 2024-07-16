/**
 */
package container.impl;

import container.ContainerModel;
import container.ContainerPackage;
import container.Stack;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link container.impl.ContainerModelImpl#getStack <em>Stack</em>}</li>
 *   <li>{@link container.impl.ContainerModelImpl#getNextContainer <em>Next Container</em>}</li>
 *   <li>{@link container.impl.ContainerModelImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link container.impl.ContainerModelImpl#getTravelDistance <em>Travel Distance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainerModelImpl extends MinimalEObjectImpl.Container implements ContainerModel {
   /**
    * The cached value of the '{@link #getStack() <em>Stack</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getStack()
    * @generated
    * @ordered
    */
   protected EList<Stack> stack;

   /**
    * The cached value of the '{@link #getNextContainer() <em>Next Container</em>}' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getNextContainer()
    * @generated
    * @ordered
    */
   protected container.Container nextContainer;

   /**
    * The cached value of the '{@link #getContainer() <em>Container</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getContainer()
    * @generated
    * @ordered
    */
   protected EList<container.Container> container;

   /**
    * The default value of the '{@link #getTravelDistance() <em>Travel Distance</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTravelDistance()
    * @generated
    * @ordered
    */
   protected static final int TRAVEL_DISTANCE_EDEFAULT = 0;

   /**
    * The cached value of the '{@link #getTravelDistance() <em>Travel Distance</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getTravelDistance()
    * @generated
    * @ordered
    */
   protected int travelDistance = TRAVEL_DISTANCE_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ContainerModelImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return ContainerPackage.Literals.CONTAINER_MODEL;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EList<Stack> getStack() {
      if (stack == null) {
         stack = new EObjectContainmentEList<Stack>(Stack.class, this, ContainerPackage.CONTAINER_MODEL__STACK);
      }
      return stack;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public container.Container getNextContainer() {
      if (nextContainer != null && nextContainer.eIsProxy()) {
         InternalEObject oldNextContainer = (InternalEObject)nextContainer;
         nextContainer = (container.Container)eResolveProxy(oldNextContainer);
         if (nextContainer != oldNextContainer) {
            if (eNotificationRequired())
               eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContainerPackage.CONTAINER_MODEL__NEXT_CONTAINER, oldNextContainer, nextContainer));
         }
      }
      return nextContainer;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public container.Container basicGetNextContainer() {
      return nextContainer;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setNextContainer(container.Container newNextContainer) {
      container.Container oldNextContainer = nextContainer;
      nextContainer = newNextContainer;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ContainerPackage.CONTAINER_MODEL__NEXT_CONTAINER, oldNextContainer, nextContainer));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public EList<container.Container> getContainer() {
      if (container == null) {
         container = new EObjectContainmentEList<container.Container>(container.Container.class, this, ContainerPackage.CONTAINER_MODEL__CONTAINER);
      }
      return container;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public int getTravelDistance() {
      return travelDistance;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setTravelDistance(int newTravelDistance) {
      int oldTravelDistance = travelDistance;
      travelDistance = newTravelDistance;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ContainerPackage.CONTAINER_MODEL__TRAVEL_DISTANCE, oldTravelDistance, travelDistance));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case ContainerPackage.CONTAINER_MODEL__STACK:
            return ((InternalEList<?>)getStack()).basicRemove(otherEnd, msgs);
         case ContainerPackage.CONTAINER_MODEL__CONTAINER:
            return ((InternalEList<?>)getContainer()).basicRemove(otherEnd, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case ContainerPackage.CONTAINER_MODEL__STACK:
            return getStack();
         case ContainerPackage.CONTAINER_MODEL__NEXT_CONTAINER:
            if (resolve) return getNextContainer();
            return basicGetNextContainer();
         case ContainerPackage.CONTAINER_MODEL__CONTAINER:
            return getContainer();
         case ContainerPackage.CONTAINER_MODEL__TRAVEL_DISTANCE:
            return getTravelDistance();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case ContainerPackage.CONTAINER_MODEL__STACK:
            getStack().clear();
            getStack().addAll((Collection<? extends Stack>)newValue);
            return;
         case ContainerPackage.CONTAINER_MODEL__NEXT_CONTAINER:
            setNextContainer((container.Container)newValue);
            return;
         case ContainerPackage.CONTAINER_MODEL__CONTAINER:
            getContainer().clear();
            getContainer().addAll((Collection<? extends container.Container>)newValue);
            return;
         case ContainerPackage.CONTAINER_MODEL__TRAVEL_DISTANCE:
            setTravelDistance((Integer)newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eUnset(int featureID) {
      switch (featureID) {
         case ContainerPackage.CONTAINER_MODEL__STACK:
            getStack().clear();
            return;
         case ContainerPackage.CONTAINER_MODEL__NEXT_CONTAINER:
            setNextContainer((container.Container)null);
            return;
         case ContainerPackage.CONTAINER_MODEL__CONTAINER:
            getContainer().clear();
            return;
         case ContainerPackage.CONTAINER_MODEL__TRAVEL_DISTANCE:
            setTravelDistance(TRAVEL_DISTANCE_EDEFAULT);
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID) {
      switch (featureID) {
         case ContainerPackage.CONTAINER_MODEL__STACK:
            return stack != null && !stack.isEmpty();
         case ContainerPackage.CONTAINER_MODEL__NEXT_CONTAINER:
            return nextContainer != null;
         case ContainerPackage.CONTAINER_MODEL__CONTAINER:
            return container != null && !container.isEmpty();
         case ContainerPackage.CONTAINER_MODEL__TRAVEL_DISTANCE:
            return travelDistance != TRAVEL_DISTANCE_EDEFAULT;
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString() {
      if (eIsProxy()) return super.toString();

      StringBuilder result = new StringBuilder(super.toString());
      result.append(" (travelDistance: ");
      result.append(travelDistance);
      result.append(')');
      return result.toString();
   }

} //ContainerModelImpl
