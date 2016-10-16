Sub generate()
    Dim sheetLength As Integer
    Dim columnLength As Integer
    Dim rowLength As Integer
    Dim sqlRow As Integer
    
    Dim dataRange As Object
    Dim sheet As Object
    
    Dim tableName As String
    Dim tempSql As String
    Dim tempValue As String
    Dim finalSql As String
    Dim cellValue As String
            
    sheetLength = Application.Sheets.Count
    sqlRow = 2
    Worksheets(1).UsedRange.Clear
    
    For i = 2 To sheetLength
        Set sheet = Worksheets(i)
        Set dataRange = sheet.UsedRange
        columnLength = dataRange.Columns.Count
        rowLength = dataRange.Rows.Count
        tableName = sheet.Name
        tempSql = "insert into " & tableName & "("
        For col = 1 To columnLength
            tempSql = tempSql & sheet.Cells(1, col).Value & ","
        Next
        tempSql = tempSql & ") values ("
        For Row = 2 To rowLength
            tempValue = ""
            For col = 1 To columnLength
                cellValue = Trim(sheet.Cells(Row, col).Value)
                If 1 > Len(cellValue) Then
                    cellValue = "null"
                End If
                tempValue = tempValue & cellValue & ","
            Next
            sqlRow = sqlRow + 1
            Worksheets(1).Cells(sqlRow, 3).Value = Replace(tempSql & tempValue & ");", ",)", ")")
        Next
        sqlRow = sqlRow + 1
    Next
    
End Sub
