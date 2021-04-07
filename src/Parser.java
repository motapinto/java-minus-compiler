import pt.up.fe.comp.TestUtils;
import parser.JmmParser;
import parser.JmmParserResult;
import report.Report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import report.ReportType;
import report.Stage;

public class Parser implements JmmParser {
    public static JmmParserResult run(String resource) throws IOException {
        String content = Utils.getResourceContent(resource, resource.substring(resource.lastIndexOf(File.separator)));
        JmmParserResult result = new Parser().parse(content);
        Utils.saveFile(Utils.getFilename(resource), "generated/json", result.toJson());

        return result;
    }

    public JmmParserResult parse(String jmmCode) {
        Jmm parser = new Jmm(Utils.toInputStream(jmmCode));
        SimpleNode root = null;

        try {
            root = parser.Program();
        } catch (ParseException e) {
            int errno = parser.getReports().size();
            Token errorToken = e.currentToken.next;
            int line = errorToken.beginLine;
            int column = errorToken.endColumn;
            String message = Jmm.getErrorMessage(errno, errorToken, e.expectedTokenSequences, e.tokenImage);

            parser.getReports().add(new Report(ReportType.ERROR, Stage.SYNTATIC, line, column, message));
        }

        if(TestUtils.getNumErrors(parser.getReports()) != 0) {
            parser.getReports().add(new Report(ReportType.ERROR, Stage.SYNTATIC, "AST Root Node is null!"));
            return new JmmParserResult(null, parser.getReports());
        }

        return new JmmParserResult(root, parser.getReports());
    }
}
