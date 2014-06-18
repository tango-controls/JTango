// +======================================================================
//   $Source$
//
//   Project:   ezTangORB
//
//   Description:  java source code for the simplified TangORB API.
//
//   $Author: Igor Khokhriakov <igor.khokhriakov@hzg.de> $
//
//   Copyright (C) :      2014
//                        Helmholtz-Zentrum Geesthacht
//                        Max-Planck-Strasse, 1, Geesthacht 21502
//                        GERMANY
//                        http://hzg.de
//
//   This file is part of Tango.
//
//   Tango is free software: you can redistribute it and/or modify
//   it under the terms of the GNU Lesser General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
//   Tango is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU Lesser General Public License for more details.
//
//   You should have received a copy of the GNU Lesser General Public License
//   along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
//  $Revision: 25721 $
//
// -======================================================================

package hzg.wpn.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 26.05.14
 */
public class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... args) {
        try {
            //TODO get from hierarchy
            clazz.getDeclaredMethod(methodName, args);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static <T extends Exception> Object invoke(Method method, Object object, Object[] args, Class<T> exceptionToThrow) throws T {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException e) {
            throw handleException(exceptionToThrow, e);
        } catch (InvocationTargetException e) {
            throw handleException(exceptionToThrow, e);
        }

    }

    private static <T extends Throwable> T handleException(Class<T> exceptionToThrow, Throwable e) {
        try {
            return exceptionToThrow.getConstructor(Throwable.class).newInstance(e);
        } catch (InstantiationException e1) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e1) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e1) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e1) {
            throw new RuntimeException(e);
        }

    }
}
