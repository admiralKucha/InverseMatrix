import java.math.BigDecimal
import java.math.RoundingMode

class Matrix {
    val matrix : MutableList<MutableList<BigDecimal>> = mutableListOf()
    var trueMatrix: MutableList<List<BigDecimal>> = mutableListOf()
    val invMatrix: MutableList<MutableList<BigDecimal>> = mutableListOf()
    var n:Int = 0
    var permutation: MutableList<List<Int>> = mutableListOf()
    var result : MutableList<MutableList<BigDecimal>> = mutableListOf()

    fun invMatrixInit()
    {
        var str: MutableList<BigDecimal>
        for (i in 0 until n)
        {
            str = mutableListOf<BigDecimal>()
            for (j in 0 until n) { if (i==j) str.add(BigDecimal.valueOf(1.0)) else str.add(BigDecimal.valueOf(0.0)) }
            invMatrix.add(str)
        }
    }

     fun input() {
        println("Enter the dimension of the matrices")
        var mod:Int
        try {n = readln().toInt() }
        catch(t:Exception){ throw Exception("Not good. You should only write a number")}
        invMatrixInit()
         for (i in 1..n) {result.add((1 .. n).map { BigDecimal.valueOf(0)}.toMutableList()) }
        println("Enter mod: 0 - manual; 1 - auto")
        try {mod = readln().toInt() }
        catch(t:Exception){ throw Exception("Not good. You should only write a number")}

        if (mod == 0) {
            for (i in 1..n)
            {
                matrix.add(readln().split(" ").map{BigDecimal.valueOf(it.toDouble())}.toMutableList())
            }
            if (matrix.size != n) throw Exception("Not good.")
        }
        else {
            for (i in 1..n) { matrix.add((1 .. n).map { BigDecimal.valueOf((0..100).random().toDouble())}.toMutableList()) }
            printMatrix()
        }
        for (str in matrix){ trueMatrix.add(str.toList()) }
         println()
        return
    }

    fun printMatrix() {
        println("MATRIX")
        for (element in matrix) {
            for (el in element) {
                print("${el} ")
            }
            println()
        }
        println()
    }

    fun prinInvMatrix() {
        println("INVMATRIX")
        for (element in invMatrix) {
            for (el in element) {
                print("${el} ")
            }
            println()
        }
        println()
    }

    fun prinCheckMatrix() {
        println("CHECKMATRIX")
        for (element in result) {
            for (el in element) {
                print("${el} ")
            }
            println()
        }
        println()
    }

    fun permutationMatrix() {
        var buf: BigDecimal
        var temp: MutableList<BigDecimal>
        var l: Int = permutation.size - 1
        var t: List<Int>
        for (el in l downTo 0) {
            t = permutation[el]
            for (k in 0 until n) {
                buf = invMatrix[k][t[1]]
                invMatrix[k][t[1]] = invMatrix[k][t[0]]
                invMatrix[k][t[0]] = buf
            }

            t = permutation[el]
            temp = invMatrix[t[1]]
            invMatrix[t[1]] = invMatrix[t[0]]
            invMatrix[t[0]] = temp
            }
        }

    fun gauss() {
        var ind: Int
        var buf: BigDecimal
        for (i in 0 until n - 1) {
            ind = i
            for (j in i until n) {
                if (matrix[i][j].abs() > matrix[i][ind].abs()) ind = j
            }
            if (ind != i) {
                for (k in 0 until n) {
                    buf = matrix[k][i]
                    matrix[k][i] = matrix[k][ind]
                    matrix[k][ind] = buf

                    buf = invMatrix[k][i]
                    invMatrix[k][i] = invMatrix[k][ind]
                    invMatrix[k][ind] = buf

                }
                permutation.add(listOf<Int>(i, ind))
            }
            for (k in i + 1 until n) {
                buf = matrix[k][i].divide(matrix[i][i], 10, RoundingMode.HALF_UP )
                for (l in 0 until n) {
                    matrix[k][l] = matrix[k][l].subtract(matrix[i][l].multiply(buf))
                    invMatrix[k][l] = invMatrix[k][l].subtract(invMatrix[i][l].multiply(buf))
                }
            }
        }

        for (i in n - 1 downTo 0) {
            for (k in n-1 downTo 0){invMatrix[i][k] =invMatrix[i][k].divide(matrix[i][i], 10, RoundingMode.HALF_UP )}
            matrix[i][i] = BigDecimal(1)
            for (k in i - 1 downTo 0) {
                buf = matrix[k][i]
                for (l in n - 1 downTo 0) {
                    matrix[k][l] =  matrix[k][l].subtract(matrix[i][l].multiply(buf))
                    invMatrix[k][l] =  invMatrix[k][l].subtract(invMatrix[i][l].multiply(buf))
                }
            }
        }
        permutationMatrix()
    }
}

fun main() {
    val a: Matrix = Matrix()
    a.input()
    a.gauss()
    a.printMatrix()
    a.prinInvMatrix()

    for (i in 0 until a.n) {
        for (j in 0 until a.n) {
            var sum = BigDecimal(0)
            for (k in 0 until a.n) {
                sum = sum.add( a.trueMatrix[i][k] * a.invMatrix[k][j])
            }
            a.result[i][j] = sum
        }
    }
    a.prinCheckMatrix()
}
