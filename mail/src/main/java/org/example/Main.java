package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] arg) {

        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.type").setLevel(Level.SEVERE);
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Something.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());

        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Something something = session.createQuery("from jos_finder_types where id = :id", Something.class)
                .setParameter("id", 1)
                .uniqueResult();

        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN_BOLD = "\033[1;32m";
        String GREEN_BOLD_BRIGHT = "\033[1;92m";
        String GREEN_UNDERLINED = "\033[4;32m";
        String WHITE_BOLD_BRIGHT = "\033[1;97m";
        String BLUE_BOLD_BRIGHT = "\033[1;94m";
        String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";

        int width = 60;
        int height = 10;

        String sth = "@@@@@@@@@@@  " + something.getTitle() + "  @@@@@@@@@@@";
        String label = (args != null && args.length > 0) ? String.join(" ", args) : sth;

        int w = Math.max(2, width);
        int h = Math.max(2, height);
        if (label.length() > w) label = label.substring(0, w);

        // top border
        System.out.println(WHITE_BOLD_BRIGHT  + "**" + "=".repeat(w) + "**" + ANSI_RESET );

        // pierwsza linia wewnętrzna z napisem na klapce
        int padLeft = (w - label.length()) / 2;
        int padRight = w - label.length() - padLeft;
        String insideLabel = " ".repeat(padLeft) + label + " ".repeat(padRight);
        System.out.println(WHITE_BOLD_BRIGHT +"||" + ANSI_RESET  +  GREEN_BOLD_BRIGHT  +  insideLabel + ANSI_RESET + WHITE_BOLD_BRIGHT +"||" + ANSI_RESET );

        // reszta wnętrza: dla każdego wiersza obliczamy pozycje dwóch przekątnych
        for (int r = 1; r < h; r++) {
            char[] row = new char[w];
            Arrays.fill(row, ' ');

            double factor = (double)(w - 1) / (h - 1);
            int left = (int) Math.round(r * factor);
            int right = (int) Math.round((h - 1 - r) * factor);

            if (left == right) {
                row[left] = 'X';
            } else {
                row[left] = '\\\';
                row[right] = '/';
            }

            System.out.println(WHITE_BOLD_BRIGHT + "||" + ANSI_RESET + BLUE_BOLD_BRIGHT + new String(row) + ANSI_RESET + WHITE_BOLD_BRIGHT + "||" + ANSI_RESET);
        }

        // bottom border
        System.out.println(WHITE_BOLD_BRIGHT + "**" + "=".repeat(w) + "**" + ANSI_RESET);

    }
}